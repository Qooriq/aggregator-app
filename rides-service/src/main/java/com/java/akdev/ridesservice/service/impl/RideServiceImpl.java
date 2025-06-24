package com.java.akdev.ridesservice.service.impl;

import com.java.akdev.commonmodels.dto.RideResponse;
import com.java.akdev.commonmodels.enumeration.OperationResult;
import com.java.akdev.ridesservice.client.CheckDriverExistClient;
import com.java.akdev.ridesservice.client.CheckPassengerExistClient;
import com.java.akdev.ridesservice.client.CheckReviewExistClient;
import com.java.akdev.ridesservice.client.WalletFeignClient;
import com.java.akdev.ridesservice.dto.RideCreateDto;
import com.java.akdev.ridesservice.dto.RideUpdateDto;
import com.java.akdev.ridesservice.entity.PassengerCoupons;
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
        var ride = rideMapper.toRide(dto);
        var distance = getDistanceFromLatLonInKm(dto.startLocation().lat(), dto.startLocation().lon(),
                dto.endLocation().lat(), dto.endLocation().lon());
        if (couponRepository.existsByCoupon(couponCode)) {
            var coupon = couponRepository.findByCoupon(couponCode);
            var hasDiscount = passengerCouponRepository
                    .existsByCouponAndPassengerId(couponCode, dto.passengerId());
            var discount = !hasDiscount ?
                            coupon.getDiscount() : 1.0;
            var rideType = ride.getRideType();
            var price = calculatePrice(rideType, distance, discount);
            String formatted = String.format("%.2f", price);
            ride.setRidePrice(Double.valueOf(formatted));
            passengerCouponRepository.save(PassengerCoupons.builder()
                    .coupon(coupon)
                    .passengerId(dto.passengerId()).build());
        } else {
            var rideType = ride.getRideType();
            var price = calculatePrice(rideType, distance, 1.0);
            ride.setRidePrice(price);
        }
        return rideMapper.toRideResponse(
                rideRepository.save(ride));
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

    private double getDistanceFromLatLonInKm(double lat1,
                                            double lon1,
                                            double lat2,
                                            double lon2)
    {
        var R = 6371d;
        var dLat = Deg2Rad(lat2 - lat1);
        var dLon = Deg2Rad(lon2 - lon1);
        var a =
                Math.sin(dLat / 2d) * Math.sin(dLat / 2d) +
                Math.cos(Deg2Rad(lat1)) * Math.cos(Deg2Rad(lat2)) *
                Math.sin(dLon / 2d) * Math.sin(dLon / 2d);
        var c = 2d * Math.atan2(Math.sqrt(a), Math.sqrt(1d - a));
        return R * c;
    }

    private double Deg2Rad(double deg)
    {
        return deg * (Math.PI / 180d);
    }

}

