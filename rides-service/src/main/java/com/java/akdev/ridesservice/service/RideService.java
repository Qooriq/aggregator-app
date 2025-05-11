package com.java.akdev.ridesservice.service;

import com.java.akdev.ridesservice.dto.RideCreateDto;
import com.java.akdev.ridesservice.dto.RideReadDto;
import com.java.akdev.ridesservice.dto.RideUpdateDto;
import com.java.akdev.ridesservice.enumeration.Order;
import com.java.akdev.ridesservice.enumeration.SortField;
import org.springframework.data.domain.Page;

public interface RideService {

    Page<RideReadDto> findAll(Integer page, Integer size, SortField sortField, Order order);

    RideReadDto findById(Long id);

    RideReadDto create(RideCreateDto dto);

    RideReadDto update(Long id, RideUpdateDto dto);

    void delete(Long id);

    RideReadDto findFirstAvailableRide();

    RideReadDto startRide(Long id);

    RideReadDto endRide(Long id);
}
