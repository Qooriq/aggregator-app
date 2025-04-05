package com.java.akdev.driverservice.service;

import com.java.akdev.driverservice.dto.DriverCreateDto;
import com.java.akdev.driverservice.dto.DriverReadDto;
import com.java.akdev.driverservice.entity.Driver;
import com.java.akdev.driverservice.enumeration.DriverStatus;
import com.java.akdev.driverservice.exception.DriverNotFoundException;
import com.java.akdev.driverservice.mapper.DriverMapper;
import com.java.akdev.driverservice.repository.DriverRepository;
import com.java.akdev.driverservice.util.SortType;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DriverService {

    private final DriverRepository driverRepository;
    private final DriverMapper driverMapper;

    @Transactional(readOnly = true)
    public Page<DriverReadDto> findAllDrivers(SortType sortType, Integer page, Integer size) {
        return driverRepository.findAll(
                PageRequest.of(page - 1, size,
                        Sort.by(sortType.getOrder(),
                                sortType.getSortField().getName())
                        )
                )
                .map(driverMapper::toDriverReadDto);
    }

    @Transactional(readOnly = true)
    public DriverReadDto findDriverById(UUID id) {
        return driverMapper.toDriverReadDto(findById(id));
    }

    @Transactional
    public DriverReadDto createDriver(DriverCreateDto dto) {
        return driverMapper.toDriverReadDto(
                driverRepository.save(driverMapper.toDriver(dto))
        );
    }

    @Transactional
    public DriverReadDto update(UUID id, DriverCreateDto dto) {
        return driverMapper.toDriverReadDto(findById(id));
    }

    @Transactional
    public void deleteDriver(UUID id) {
        var driver = findById(id);
        driver.setStatus(DriverStatus.DELETED);
        driverRepository.save(driver);
    }

    private Driver findById(UUID id) {
        return driverRepository.findById(id)
                .orElseThrow(DriverNotFoundException::new);
    }

}
