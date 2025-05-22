package com.java.akdev.driverservice.service;

import com.java.akdev.commonmodels.dto.UserResponse;
import com.java.akdev.driverservice.dto.DriverCreateDto;
import com.java.akdev.driverservice.enumeration.Order;
import com.java.akdev.driverservice.enumeration.SortField;
import org.springframework.data.domain.Page;

import java.util.UUID;

public interface DriverService {

    Page<UserResponse> findAll(Integer page, Integer size, SortField sortField, Order order);

    UserResponse findDriverById(UUID id);

    UserResponse createDriver(DriverCreateDto dto);

    UserResponse update(UUID id, DriverCreateDto dto);

    void deleteDriver(UUID id);
}
