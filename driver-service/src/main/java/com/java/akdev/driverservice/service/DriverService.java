package com.java.akdev.driverservice.service;

import com.java.akdev.driverservice.dto.DriverCreateDto;
import com.java.akdev.driverservice.dto.DriverReadDto;
import com.java.akdev.driverservice.enumeration.Order;
import com.java.akdev.driverservice.enumeration.SortField;
import org.springframework.data.domain.Page;

import java.util.UUID;

public interface DriverService {

    Page<DriverReadDto> findAll(Integer page, Integer size, SortField sortField, Order order);

    DriverReadDto findDriverById(UUID id);

    DriverReadDto createDriver(DriverCreateDto dto);

    DriverReadDto update(UUID id, DriverCreateDto dto);

    void deleteDriver(UUID id);
}
