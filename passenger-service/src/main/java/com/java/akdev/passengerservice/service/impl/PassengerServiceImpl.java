package com.java.akdev.passengerservice.service.impl;


import com.java.akdev.passengerservice.dto.PassengerCreateDto;
import com.java.akdev.passengerservice.dto.PassengerReadDto;
import com.java.akdev.passengerservice.entity.Passenger;
import com.java.akdev.passengerservice.enumeration.Order;
import com.java.akdev.passengerservice.enumeration.PassengerStatus;
import com.java.akdev.passengerservice.enumeration.SortField;
import com.java.akdev.passengerservice.exception.PassengerNotFoundException;
import com.java.akdev.passengerservice.mapper.PassengerMapper;
import com.java.akdev.passengerservice.repository.PassengerRepository;
import com.java.akdev.passengerservice.service.PassengerService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PassengerServiceImpl implements PassengerService {

    private final PassengerRepository passengerRepository;
    private final PassengerMapper passengerMapper;

    private static final String MESSAGE = "PassengerController.passenger.notFound";

    @Transactional(readOnly = true)
    public Page<PassengerReadDto> findAll(Integer page, Integer size, SortField sortField, Order order) {
        var direction = getDirection(order);
        var req = PageRequest.of(page, size, direction, sortField.getName());
        return passengerRepository.findAll(req)
                .map(passengerMapper::toReadDto);
    }

    public PassengerReadDto findPassengerById(UUID id) {
        var passenger = findPassenger(id);
        return passengerMapper.toReadDto(passenger);
    }

    @Transactional
    public PassengerReadDto updatePassenger(UUID id, PassengerCreateDto dto) {
        return passengerRepository.findById(id)
                .map(passenger -> {
                    passengerMapper.map(passenger, dto);
                    passengerRepository.save(passenger);
                    return passengerMapper.toReadDto(passenger);
                })
                .orElseThrow(() -> new PassengerNotFoundException(MESSAGE));
    }

    public PassengerReadDto createPassenger(PassengerCreateDto dto) {
        return passengerMapper.toReadDto(passengerRepository
                .save(passengerMapper.toPassenger(dto)));
    }

    @Transactional
    public void deletePassenger(UUID id) {
        var passenger = findPassenger(id);
        passenger.setStatus(PassengerStatus.DELETED);
        passengerRepository.save(passenger);
    }

    private Sort.Direction getDirection(Order order) {
        return order == Order.DESC ? Sort.Direction.DESC : Sort.Direction.ASC;
    }

    private Passenger findPassenger(UUID id) {
        return passengerRepository.findById(id)
                .orElseThrow(() -> new PassengerNotFoundException(MESSAGE));
    }
}
