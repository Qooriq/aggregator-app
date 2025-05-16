package com.java.akdev.ridesservice.service.impl;

import com.java.akdev.ridesservice.client.WalletFeignClient;
import com.java.akdev.ridesservice.dto.RideCreateDto;
import com.java.akdev.ridesservice.dto.RideReadDto;
import com.java.akdev.ridesservice.dto.RideUpdateDto;
import com.java.akdev.ridesservice.enumeration.*;
import com.java.akdev.ridesservice.exception.NotEnoughMoneyException;
import com.java.akdev.ridesservice.exception.RideNotFoundException;
import com.java.akdev.ridesservice.mapper.RideMapper;
import com.java.akdev.ridesservice.repository.RideRepository;
import com.java.akdev.ridesservice.service.RideService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RideServiceImpl implements RideService {

    private final RideRepository rideRepository;
    private final WalletFeignClient client;
    private final RideMapper rideMapper;

    private final String MESSAGE = "message.rideNotFound.error";

    @Transactional(readOnly = true)
    public Page<RideReadDto> findAll(Integer page, Integer size, SortField sortField, Order order) {
        Sort.Direction dir = getDirection(order);
        var req = PageRequest.of(page - 1,
                size,
                dir,
                sortField.getName());
        return rideRepository.findAll(req)
                .map(rideMapper::toRideReadDto);
    }

    @Transactional(readOnly = true)
    public RideReadDto findById(Long id) {
        return rideRepository.findById(id)
                .map(rideMapper::toRideReadDto)
                .orElseThrow(() -> new RideNotFoundException("message.rideNotFound.error"));
    }

    @Transactional
    public RideReadDto create(RideCreateDto dto) {
        var ride = rideMapper.toRide(dto);
        ride.setRidePrice(10.0);
        return rideMapper.toRideReadDto(
                rideRepository.save(ride));
    }

    @Transactional
    public RideReadDto update(Long id, RideUpdateDto dto) {
        return rideRepository.findById(id)
                .map(ride -> rideMapper.updateRide(ride, dto))
                .map(rideRepository::save)
                .map(rideMapper::toRideReadDto)
                .orElseThrow(() -> new RideNotFoundException("message.rideNotFound.error"));
    }

    @Transactional
    public void delete(Long id) {
        rideRepository.deleteById(id);
    }

    @Override
    public RideReadDto startRide(Long id) {
        var ride = rideRepository.findById(id)
                .orElseThrow(() -> new RideNotFoundException(MESSAGE));
        ride.setStatus(RideStatus.IN_PROGRESS);
        rideRepository.save(ride);
        return rideMapper.toRideReadDto(ride);
    }

    @Override
    public RideReadDto endRide(Long id) {
        var ride = rideRepository.findById(id)
                .orElseThrow(() -> new RideNotFoundException(MESSAGE));
        var passengerId = ride.getPassengerId();
        if (ride.getPaymentMethod() == PaymentMethod.CARD) {
            var response = client.updateWallet(passengerId, ride.getRidePrice());
            if (response.getBody().message().equals(OperationResult.DECLINED)) {
                ride.setPaymentMethod(PaymentMethod.CASH);
                rideRepository.save(ride);
                throw new NotEnoughMoneyException("message.notEnoughMoney.error");
            }
        }
        ride.setStatus(RideStatus.COMPLETED);
        return rideMapper.toRideReadDto(rideRepository.save(ride));
    }

    @Override
    public RideReadDto findFirstAvailableRide() {
        var req = PageRequest.of(0, 10);
        var rides = rideRepository.findAllByStatus(RideStatus.PENDING, req);
        return rideMapper.toRideReadDto(rides.stream().findFirst()
                .orElseThrow(() -> new RideNotFoundException(MESSAGE)));
    }

    private Sort.Direction getDirection(Order order) {
        return order == Order.ASC ? Sort.Direction.ASC : Sort.Direction.DESC;
    }

}

