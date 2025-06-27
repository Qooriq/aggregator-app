package com.java.akdev.passengerservice.service;


import com.java.akdev.commonmodels.dto.UserResponse;
import com.java.akdev.passengerservice.dto.PassengerCreateDto;
import com.java.akdev.passengerservice.enumeration.Order;
import com.java.akdev.passengerservice.enumeration.SortField;
import org.springframework.data.domain.Page;

import java.util.UUID;

public interface PassengerService {

    Page<UserResponse> findAll(Integer page, Integer size, SortField sortField, Order order);

    UserResponse findPassengerById(UUID id);

    UserResponse updatePassenger(UUID id, PassengerCreateDto dto);

    UserResponse createPassenger(PassengerCreateDto dto);

    void deletePassenger(UUID id);
}
