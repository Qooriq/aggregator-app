package com.java.akdev.passengerservice.service;

import com.java.akdev.passengerservice.dto.PassengerCreateDto;
import com.java.akdev.passengerservice.dto.PassengerReadDto;
import com.java.akdev.passengerservice.enumeration.Order;
import com.java.akdev.passengerservice.enumeration.SortField;
import org.springframework.data.domain.Page;

import java.util.UUID;

public interface PassengerService {

    Page<PassengerReadDto> findAll(Integer page, Integer size, SortField sortField, Order order);

    PassengerReadDto findPassengerById(UUID id);

    PassengerReadDto updatePassenger(UUID id, PassengerCreateDto dto);

    PassengerReadDto createPassenger(PassengerCreateDto dto);

    void deletePassenger(UUID id);
}
