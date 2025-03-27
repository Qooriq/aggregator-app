package com.java.akdev.passengerservice.service;


import com.java.akdev.passengerservice.dto.PassengerCreateDto;
import com.java.akdev.passengerservice.dto.PassengerReadDto;
import com.java.akdev.passengerservice.enumeration.PassengerStatus;
import com.java.akdev.passengerservice.exception.PassengerNotFoundException;
import com.java.akdev.passengerservice.mapper.PassengerMapper;
import com.java.akdev.passengerservice.repository.PassengerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PassengerService {

    private final PassengerRepository passengerRepository;
    private final PassengerMapper passengerMapper;

    @Transactional(readOnly = true)
    public Page<PassengerReadDto> findAll(Integer page, Integer size) {
        return passengerRepository.findAll(PageRequest.of(page - 1, size))
                .map(passengerMapper::toReadDto);
    }

    @Transactional(readOnly = true)
    public PassengerReadDto findPassengerById(UUID id) {
        var passenger = passengerRepository.findById(id);
        return passenger.map(passengerMapper::toReadDto).orElseThrow(PassengerNotFoundException::new);
    }

    @Transactional
    public PassengerReadDto updatePassenger(UUID id, PassengerCreateDto dto) {
        var passenger = passengerRepository.findById(id).orElseThrow(PassengerNotFoundException::new);
        passengerMapper.map(passenger, dto);
        return passengerMapper.toReadDto(passenger);
    }

    @Transactional
    public PassengerReadDto createPassenger(PassengerCreateDto dto) {
        return passengerMapper.toReadDto(passengerRepository.save(passengerMapper.toPassenger(dto)));
    }

    @Transactional
    public void deletePassenger(UUID id) {
        var passenger = passengerRepository.findById(id).orElseThrow(PassengerNotFoundException::new);
        passenger.setStatus(PassengerStatus.DELETED);
        passengerRepository.save(passenger);
    }
}
