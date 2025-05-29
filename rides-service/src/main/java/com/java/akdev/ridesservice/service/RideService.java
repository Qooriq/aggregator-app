package com.java.akdev.ridesservice.service;

import com.java.akdev.commonmodels.dto.RideResponse;
import com.java.akdev.ridesservice.dto.RideCreateDto;
import com.java.akdev.ridesservice.dto.RideUpdateDto;
import com.java.akdev.ridesservice.enumeration.Order;
import com.java.akdev.ridesservice.enumeration.SortField;
import org.springframework.data.domain.Page;


public interface RideService {

    Page<RideResponse> findAll(Integer page, Integer size, SortField sortField, Order order);

    RideResponse findById(Long id);

    RideResponse create(RideCreateDto dto);

    RideResponse update(Long id, RideUpdateDto dto);

    void delete(Long id);

    RideResponse findFirstAvailableRide();

    RideResponse startRide(Long id);

    RideResponse endRide(Long id);
}
