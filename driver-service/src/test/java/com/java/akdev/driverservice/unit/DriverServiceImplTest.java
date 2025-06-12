package com.java.akdev.driverservice.unit;

import com.java.akdev.commonmodels.dto.UserResponse;
import com.java.akdev.driverservice.dto.DriverCreateDto;
import com.java.akdev.driverservice.entity.Driver;
import com.java.akdev.driverservice.exception.DriverNotFoundException;
import com.java.akdev.driverservice.mapper.DriverMapper;
import com.java.akdev.driverservice.repository.DriverRepository;
import com.java.akdev.driverservice.service.impl.DriverServiceImpl;
import com.java.akdev.driverservice.util.TestSetUps;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DriverServiceImplTest {

    @Mock
    private DriverMapper driverMapper;
    @Mock
    private DriverRepository driverRepository;
    @InjectMocks
    private DriverServiceImpl driverService;

    private Driver driver;
    private Driver updateDriver;
    private DriverCreateDto driverCreateDto;
    private UserResponse driverUpdateDto;
    private UserResponse driverReadDto;
    private UUID id;

    @BeforeEach
    void setUp() {
        driver = TestSetUps.getDriver();
        id = TestSetUps.id;
        driverCreateDto = TestSetUps.getCreateDto();
        driverReadDto = TestSetUps.getReadDto();
        driverUpdateDto = TestSetUps.getUpdateDto();
        updateDriver = TestSetUps.getUpdateDriver();
    }

    @Test
    @DisplayName("find driver by id")
    void findDriverById() {

        when(driverRepository.findById(id))
                .thenReturn(Optional.of(driver));
        when(driverMapper.toUserResponse(driver))
                .thenReturn(driverReadDto);

        var driv = driverService.findDriverById(id);

        assertThat(driv)
                .isEqualTo(driverReadDto);

        verify(driverMapper).toUserResponse(driver);
        verify(driverRepository).findById(id);
    }

    @Test
    @DisplayName("driver not found exception")
    void findDriverNotFoundById() {

        when(driverRepository.findById(id))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() ->
                driverService.findDriverById(id)
        ).isInstanceOf(DriverNotFoundException.class);

        verify(driverRepository).findById(id);
    }

    @Test
    @DisplayName("create driver")
    void createDriver() {
        when(driverRepository.save(driver))
                .thenReturn(driver);
        when(driverMapper.toDriver(driverCreateDto))
                .thenReturn(driver);
        when(driverMapper.toUserResponse(driver))
                .thenReturn(driverReadDto);

        var driv = driverService.createDriver(driverCreateDto);

        assertThat(driv)
                .isEqualTo(driverReadDto);

        verify(driverRepository).save(driver);
        verify(driverMapper).toDriver(driverCreateDto);
        verify(driverMapper).toUserResponse(driver);
    }

    @Test
    @DisplayName("update driver by id")
    void update() {
        when(driverRepository.findById(id))
                .thenReturn(Optional.of(driver));
        when(driverRepository.save(driver))
                .thenReturn(updateDriver);
        when(driverMapper.toUserResponse(updateDriver))
                .thenReturn(driverUpdateDto);

        var drivDto = driverService.update(id, driverCreateDto);

        assertThat(drivDto)
                .isEqualTo(driverUpdateDto);

        verify(driverRepository).findById(id);
        verify(driverRepository).save(driver);
        verify(driverMapper).toUserResponse(updateDriver);
    }

    @Test
    @DisplayName("delete driver by id")
    void deleteDriver() {
        when(driverRepository.findById(id))
                .thenReturn(Optional.of(driver));

        driverService.deleteDriver(id);

        verify(driverRepository).findById(id);
    }
}