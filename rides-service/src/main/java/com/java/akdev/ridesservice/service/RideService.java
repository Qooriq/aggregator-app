package com.java.akdev.ridesservice.service;

import com.java.akdev.ridesservice.dto.RideCreateDto;
import com.java.akdev.ridesservice.dto.RideReadDto;
import com.java.akdev.ridesservice.dto.RideUpdateDto;
import com.java.akdev.ridesservice.exception.RideNotFoundException;
import com.java.akdev.ridesservice.mapper.RideMapper;
import com.java.akdev.ridesservice.repository.RideRepository;
import com.java.akdev.ridesservice.util.SortType;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RideService {

    private final RideRepository rideRepository;
    private final RideMapper rideMapper;

    @Transactional(readOnly = true)
    public Page<RideReadDto> findAll(Integer page, Integer size, SortType sortType) {
        return rideRepository.findAll(
                        PageRequest.of(page - 1,
                                size,
                                sortType.getOrder(),
                                sortType.getSortField().getName())
                )
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
                .map(ride -> rideMapper.map(ride, dto))
                .map(rideMapper::toRideReadDto)
                .orElseThrow(() -> new RideNotFoundException("message.rideNotFound.error"));
    }

    @Transactional
    public void delete(Long id) {
        rideRepository.deleteById(id);
    }

}
