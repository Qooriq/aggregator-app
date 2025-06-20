package com.java.akdev.ridesservice.service.impl;

import com.java.akdev.commonmodels.dto.RideResponse;
import com.java.akdev.commonmodels.enumeration.OperationResult;
import com.java.akdev.ridesservice.client.CheckDriverExistClient;
import com.java.akdev.ridesservice.client.CheckPassengerExistClient;
import com.java.akdev.ridesservice.client.CheckReviewExistClient;
import com.java.akdev.ridesservice.client.WalletFeignClient;
import com.java.akdev.ridesservice.dto.RideCreateDto;
import com.java.akdev.ridesservice.dto.RideUpdateDto;
import com.java.akdev.ridesservice.enumeration.*;
import com.java.akdev.ridesservice.exception.NotEnoughMoneyException;
import com.java.akdev.ridesservice.exception.EntityNotFound;
import com.java.akdev.ridesservice.mapper.RideMapper;
import com.java.akdev.ridesservice.repository.CouponRepository;
import com.java.akdev.ridesservice.repository.PassengerCouponRepository;
import com.java.akdev.ridesservice.repository.RideRepository;
import com.java.akdev.ridesservice.service.RideService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class RideServiceImpl implements RideService {

    private final RideRepository rideRepository;
    private final PassengerCouponRepository passengerCouponRepository;
    private final CouponRepository couponRepository;
    private final RideMapper rideMapper;
    private final CheckPassengerExistClient passengerClient;
    private final CheckDriverExistClient driverClient;
    private final CheckReviewExistClient reviewClient;
    private final WalletFeignClient walletClient;
    private final static String EXCEPTION = "message.rideNotFound.error";

    @Transactional(readOnly = true)
    public Page<RideResponse> findAll(Integer page, Integer size, SortField sortField, Order order) {
        Sort.Direction dir = getDirection(order);
        var req = PageRequest.of(page - 1,
                size,
                dir,
                sortField.getName());
        return rideRepository.findAll(req)
                .map(rideMapper::toRideResponse);
    }

    @Transactional(readOnly = true)
    public RideResponse findById(Long id) {
        return rideRepository.findById(id)
                .map(rideMapper::toRideResponse)
                .orElseThrow(() -> new EntityNotFound(EXCEPTION));
    }

    @Transactional
    public RideResponse create(RideCreateDto dto, String couponCode) {
        passengerClient.findPassengerById(dto.passengerId());
        if (couponRepository.existsByCoupon(couponCode)) {
            var ride = rideMapper.toRide(dto);
            var hasDiscount = passengerCouponRepository
                    .existsByCouponNameAndPassengerId(couponCode, dto.passengerId());

            var discount = hasDiscount ? couponRepository
                    .findByCoupon(couponCode)
                            .getDiscount() : 1.0;
            var rideType = ride.getRideType();
            var distance = 1.0;
            var price = calculatePrice(rideType, distance, discount);
            ride.setRidePrice(price);
            return rideMapper.toRideResponse(
                    rideRepository.save(ride));
        } else {
            var ride = rideMapper.toRide(dto);
            var rideType = ride.getRideType();
            var distance = 1.0;
            var price = calculatePrice(rideType, distance, 1.0);
            ride.setRidePrice(price);
            return rideMapper.toRideResponse(
                    rideRepository.save(ride));
        }

    }

    @Transactional
    public RideResponse update(Long id, RideUpdateDto dto) {
        passengerClient.findPassengerById(dto.passengerId());
        driverClient.findDriverById(dto.driverId());
        reviewClient.findReviewById(dto.passengerReviewDriver());
        reviewClient.findReviewById(dto.driverReviewPassenger());
        return rideRepository.findById(id)
                .map(ride -> rideMapper.updateRide(ride, dto))
                .map(rideRepository::save)
                .map(rideMapper::toRideResponse)
                .orElseThrow(() -> new EntityNotFound(EXCEPTION));
    }

    @Transactional
    public void delete(Long id) {
        rideRepository.deleteById(id);
    }

    @Override
    public RideResponse startRide(Long id) {
        var ride = rideRepository.findById(id)
                .orElseThrow(() -> new EntityNotFound(EXCEPTION));
        ride.setStatus(RideStatus.IN_PROGRESS);
        ride.setStartTime(Instant.now());
        rideRepository.save(ride);
        return rideMapper.toRideResponse(ride);
    }

    @Override
    public RideResponse endRide(Long id) {
        var ride = rideRepository.findById(id)
                .orElseThrow(() -> new EntityNotFound(EXCEPTION));
        var passengerId = ride.getPassengerId();
        if (ride.getPaymentMethod() == PaymentMethod.CARD) {
            var response = walletClient.updateWallet(passengerId, ride.getRidePrice());
            if (response.getBody().message().equals(OperationResult.DECLINED)) {
                ride.setPaymentMethod(PaymentMethod.CASH);
                rideRepository.save(ride);
                throw new NotEnoughMoneyException("message.notEnoughMoney.error");
            }
        }
        ride.setStatus(RideStatus.COMPLETED);
        return rideMapper.toRideResponse(rideRepository.save(ride));
    }

    @Override
    public RideResponse findFirstAvailableRide() {
        var req = PageRequest.of(0, 10);
        var rides = rideRepository.findAllByStatus(RideStatus.PENDING, req);
        return rideMapper.toRideResponse(rides.stream().findFirst()
                .orElseThrow(() -> new EntityNotFound(EXCEPTION)));
    }

    private Double calculatePrice(RideType rideType, double distance, double discount) {
        return rideType.getCoefficient() * distance * discount;
    }

    private Sort.Direction getDirection(Order order) {
        return order == Order.ASC ? Sort.Direction.ASC : Sort.Direction.DESC;
    }

}

