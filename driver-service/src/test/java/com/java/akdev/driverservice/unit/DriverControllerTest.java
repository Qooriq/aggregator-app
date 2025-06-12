package com.java.akdev.driverservice.unit;

import com.java.akdev.driverservice.controller.DriverController;
import com.java.akdev.driverservice.dto.DriverCreateDto;
import com.java.akdev.commonmodels.dto.UserResponse;
import com.java.akdev.driverservice.service.DriverService;
import com.java.akdev.driverservice.util.TestSetUps;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DriverControllerTest {

    @Mock
    private DriverService driverService;
    @InjectMocks
    private DriverController driverController;

    private UUID id;
    private DriverCreateDto driverCreateDto;
    private UserResponse driverReadDto;

    @BeforeEach
    void setUp() {
        id = TestSetUps.id;
        driverCreateDto = TestSetUps.getCreateDto();
        driverReadDto = TestSetUps.getReadDto();
    }

    @Test
    void getDriver() {
        when(driverService.findDriverById(id))
                .thenReturn(driverReadDto);

        var driver = driverController.findById(id);

        assertThat(driver.getBody()).
                isEqualTo(driverReadDto);

        verify(driverService).findDriverById(id);
    }

    @Test
    void createDriver() {
        when(driverService.createDriver(driverCreateDto))
                .thenReturn(driverReadDto);

        var driver = driverController.create(driverCreateDto);

        assertThat(driver.getBody())
                .isEqualTo(driverReadDto);

        verify(driverService).createDriver(driverCreateDto);
    }

    @Test
    void updateDriver() {
        when(driverService.update(id, driverCreateDto))
                .thenReturn(driverReadDto);

        var driver = driverController.update(id, driverCreateDto);

        assertThat(driver.getBody())
                .isEqualTo(driverReadDto);

        verify(driverService).update(id, driverCreateDto);
    }

    @Test
    void deleteDriver() {
        doNothing().when(driverService).deleteDriver(id);

        driverController.delete(id);

        verify(driverService).deleteDriver(id);
    }

}
