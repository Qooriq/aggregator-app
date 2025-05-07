package com.java.akdev.ridesservice.service.impl;

import com.java.akdev.ridesservice.dto.RideCreateDto;
import com.java.akdev.ridesservice.dto.RideReadDto;
import com.java.akdev.ridesservice.dto.RideUpdateDto;
import com.java.akdev.ridesservice.enumeration.Order;
import com.java.akdev.ridesservice.enumeration.SortField;
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
    private final RideMapper rideMapper;

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
        return rideMapper.toRideReadDto(
                rideRepository.save(rideMapper.toRide(dto))
        );
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

    private Sort.Direction getDirection(Order order) {
        return order == Order.ASC ? Sort.Direction.ASC : Sort.Direction.DESC;
    }

}

