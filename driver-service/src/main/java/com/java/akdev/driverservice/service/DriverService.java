package com.java.akdev.driverservice.service;

import com.java.akdev.driverservice.dto.DriverCreateDto;
import com.java.akdev.driverservice.dto.DriverReadDto;
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
        return driverRepository.findById(id)
                .map(driverMapper::toDriverReadDto)
                .orElseThrow(DriverNotFoundException::new);
    }

    @Transactional
    public DriverReadDto createDriver(DriverCreateDto dto) {
        return driverMapper.toDriverReadDto(
                driverRepository.save(driverMapper.toDriver(dto))
        );
    }

    @Transactional
    public DriverReadDto update(UUID id, DriverCreateDto dto) {
        return driverRepository.findById(id)
                .map(driver -> {
                    driverMapper.map(driver, dto);
                    return driver;
                })
                .map(driverRepository::save)
                .map(driverMapper::toDriverReadDto)
                .orElseThrow(DriverNotFoundException::new);
    }

    @Transactional
    public void deleteDriver(UUID id) {
        var driver = driverRepository.findById(id)
                .orElseThrow(DriverNotFoundException::new);
        driver.setStatus(DriverStatus.DELETED);
        driverRepository.save(driver);
    }

}
