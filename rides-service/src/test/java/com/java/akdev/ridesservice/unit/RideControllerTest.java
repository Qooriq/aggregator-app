package com.java.akdev.ridesservice.unit;

import com.java.akdev.ridesservice.controller.RideController;
import com.java.akdev.ridesservice.dto.RideCreateDto;
import com.java.akdev.ridesservice.dto.RideReadDto;
import com.java.akdev.ridesservice.dto.RideUpdateDto;
import com.java.akdev.ridesservice.service.RideService;
import com.java.akdev.ridesservice.util.TestSetUps;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RideControllerTest {

    @Mock
    private RideService rideService;
    @InjectMocks
    private RideController rideController;

    private Long id;
    private RideCreateDto rideCreateDto;
    private RideUpdateDto rideUpdateDto;
    private RideReadDto rideReadDto;

    @BeforeEach
    void setUp() {
        id = TestSetUps.id;
        rideCreateDto = TestSetUps.getCreateDto();
        rideReadDto = TestSetUps.getReadDto();
        rideUpdateDto = TestSetUps.getRideUpdateDto();
    }

    @Test
    void findRideById() {
        when(rideService.findById(id))
                .thenReturn(rideReadDto);

        var ride = rideController.findById(id);

        assertThat(ride.getBody())
                .isEqualTo(rideReadDto);

        verify(rideService).findById(id);
    }

    @Test
    void createRide() {
        when(rideService.create(rideCreateDto))
                .thenReturn(rideReadDto);

        var ride = rideController.create(rideCreateDto);

        assertThat(ride.getBody())
                .isEqualTo(rideReadDto);

        verify(rideService).create(rideCreateDto);
    }

    @Test
    void updateRide() {
        when(rideService.update(id, rideUpdateDto))
                .thenReturn(rideReadDto);

        var ride = rideController.update(id, rideUpdateDto);

        assertThat(ride.getBody())
                .isEqualTo(rideReadDto);

        verify(rideService).update(id, rideUpdateDto);
    }

    @Test
    void deleteRide() {
        doNothing().when(rideService).delete(id);

        rideController.delete(id);

        verify(rideService).delete(id);
    }
}
