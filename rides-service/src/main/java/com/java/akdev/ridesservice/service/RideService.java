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

@Service
@RequiredArgsConstructor
public class RideService {

    private final RideRepository rideRepository;
    private final RideMapper rideMapper;

    public Page<RideReadDto> findAll(Integer page, Integer size, SortType sortType) {
        return rideRepository.findAll(
                        PageRequest.of(page,
                                size,
                                sortType.getOrder(),
                                sortType.getSortField().getName())
                )
                .map(rideMapper::toRideReadDto);
    }

    public RideReadDto findById(Long id) {
        return rideRepository.findById(id)
                .map(rideMapper::toRideReadDto)
                .orElseThrow(RideNotFoundException::new);
    }

    public RideReadDto create(RideCreateDto dto) {
        return rideMapper.toRideReadDto(
                rideRepository.save(rideMapper.toRide(dto))
        );
    }

    public RideReadDto update(Long id, RideUpdateDto dto) {
        return rideRepository.findById(id)
                .map(ride -> {
                    rideMapper.map(ride, dto);
                    rideRepository.save(ride);
                    return rideMapper.toRideReadDto(ride);
                })
                .orElseThrow(RideNotFoundException::new);
    }

    public void delete(Long id) {
        rideRepository.deleteById(id);
    }

}
