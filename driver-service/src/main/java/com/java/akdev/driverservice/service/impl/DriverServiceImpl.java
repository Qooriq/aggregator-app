package com.java.akdev.driverservice.service.impl;

import com.java.akdev.commonmodels.dto.UserResponse;
import com.java.akdev.driverservice.dto.DriverCreateDto;
import com.java.akdev.driverservice.entity.Driver;
import com.java.akdev.driverservice.enumeration.DriverStatus;
import com.java.akdev.driverservice.enumeration.ExceptionMessages;
import com.java.akdev.driverservice.enumeration.Order;
import com.java.akdev.driverservice.enumeration.SortField;
import com.java.akdev.driverservice.exception.DriverNotFoundException;
import com.java.akdev.driverservice.exception.PhoneAlreadyExistsException;
import com.java.akdev.driverservice.exception.UsernameAlreadyExistsException;
import com.java.akdev.driverservice.mapper.DriverMapper;
import com.java.akdev.driverservice.repository.DriverRepository;
import com.java.akdev.driverservice.service.DriverService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DriverServiceImpl implements DriverService {

    private final DriverRepository driverRepository;
    private final DriverMapper driverMapper;

    @Transactional(readOnly = true)
    public Page<UserResponse> findAll(Integer page,
                                      Integer size,
                                      SortField sortField,
                                      Order order) {
        var direction = getDirection(order);
        var req = PageRequest.of(page - 1, size, direction, sortField.getName());
        return driverRepository.findAll(req)
                .map(driverMapper::toUserResponse);
    }

    public UserResponse findDriverById(UUID id) {
        return driverMapper.toUserResponse(findById(id));
    }

    public UserResponse createDriver(DriverCreateDto dto) {
        if (driverRepository.existsByUsername(dto.username())) {
            throw new UsernameAlreadyExistsException(ExceptionMessages.ALREADY_EXISTS.getName());
        }
        if (Objects.nonNull(dto.phoneNumber()) &&
            driverRepository.existsByPhoneNumber(dto.phoneNumber())) {
            throw new PhoneAlreadyExistsException(ExceptionMessages.ALREADY_EXISTS.getName());
        }
        var driver = driverMapper.toDriver(dto);
        var res = driverRepository.save(driver);
        return driverMapper.toUserResponse(res);
    }

    @Transactional
    public UserResponse update(UUID id, DriverCreateDto dto) {
        if (driverRepository.existsByUsername(dto.username())) {
            throw new UsernameAlreadyExistsException(ExceptionMessages.ALREADY_EXISTS.getName());
        }
        if (Objects.nonNull(dto.phoneNumber()) &&
                     driverRepository.existsByPhoneNumber(dto.phoneNumber())) {
            throw new PhoneAlreadyExistsException(ExceptionMessages.ALREADY_EXISTS.getName());
        }
        var driver = findById(id);
        driverMapper.updateDriver(driver, dto);
        driverRepository.save(driver);
        return driverMapper.toUserResponse(driver);
    }

    @Transactional
    public void deleteDriver(UUID id) {
        var driver = findById(id);
        driver.setStatus(DriverStatus.DELETED);
        driverRepository.save(driver);
    }

    private Driver findById(UUID id) {
        return driverRepository.findById(id)
                .orElseThrow(() -> new DriverNotFoundException(ExceptionMessages.DRIVER_NOT_FOUND.getName()));
    }

    private Sort.Direction getDirection(Order order) {
        return order == Order.DESC ? Sort.Direction.DESC : Sort.Direction.ASC;
    }
}
