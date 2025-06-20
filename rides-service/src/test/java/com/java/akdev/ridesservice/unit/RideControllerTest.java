package com.java.akdev.ridesservice.unit;

import com.java.akdev.commonmodels.dto.RideResponse;
import com.java.akdev.ridesservice.controller.RideController;
import com.java.akdev.ridesservice.dto.RideCreateDto;
import com.java.akdev.ridesservice.dto.RideUpdateDto;
import com.java.akdev.ridesservice.service.RideService;
import com.java.akdev.ridesservice.util.TestSetUps;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RideControllerTest {

    @Mock
    private RideService rideService;
    @InjectMocks
    private RideController rideController;

    private Long id;
    private RideCreateDto rideCreateDto;
    private RideUpdateDto rideUpdateDto;
    private RideResponse expectedResult;

    @BeforeEach
    void setUp() {
        id = TestSetUps.ID;
        rideCreateDto = TestSetUps.getCreateDto();
        expectedResult = TestSetUps.getReadDto();
        rideUpdateDto = TestSetUps.getRideUpdateDto();
    }

    @Test
    @DisplayName("find ride by id")
    void givenId_findById_returnUser() {
        doReturn(expectedResult).when(rideService).findById(id);

        var actual = rideController.findById(id);

        assertThat(actual.getBody())
                .isEqualTo(expectedResult);

        verify(rideService).findById(id);
    }

    //TODO: remake with coupon
    @Test
    @DisplayName("create ride with payload")
    void givenRidePayload_create_returnCreatedUser() {
        when(rideService.create(rideCreateDto, " "))
                .thenReturn(expectedResult);

        var actual = rideController.create(rideCreateDto, " ");

        assertThat(actual.getBody())
                .isEqualTo(expectedResult);

        verify(rideService).create(rideCreateDto, " ");
    }

    @Test
    @DisplayName("update ride by id")
    void givenRideIdAndRideUpdatePayload_update_returnUpdatedUser() {
        when(rideService.update(id, rideUpdateDto))
                .thenReturn(expectedResult);

        var actual = rideController.update(id, rideUpdateDto);

        assertThat(actual.getBody())
                .isEqualTo(expectedResult);

        verify(rideService).update(id, rideUpdateDto);
    }

    @Test
    @DisplayName("delete ride by id")
    void givenRideId_delete_nothingReturn() {
        doNothing().when(rideService).delete(id);

        rideController.delete(id);

        verify(rideService).delete(id);
    }
}
