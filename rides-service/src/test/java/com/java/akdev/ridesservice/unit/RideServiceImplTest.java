package com.java.akdev.ridesservice.unit;

import com.java.akdev.ridesservice.dto.RideCreateDto;
import com.java.akdev.ridesservice.dto.RideReadDto;
import com.java.akdev.ridesservice.dto.RideUpdateDto;
import com.java.akdev.ridesservice.entity.Ride;
import com.java.akdev.ridesservice.exception.RideNotFoundException;
import com.java.akdev.ridesservice.mapper.RideMapper;
import com.java.akdev.ridesservice.repository.RideRepository;
import com.java.akdev.ridesservice.service.impl.RideServiceImpl;
import com.java.akdev.ridesservice.util.TestSetUps;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class RideServiceImplTest {

    @Mock
    private RideMapper rideMapper;
    @Mock
    private RideRepository rideRepository;
    @InjectMocks
    private RideServiceImpl reviewService;

    private Ride ride;
    private Ride updateRide;
    private RideCreateDto rideCreateDto;
    private RideReadDto expectedResultInUpdate;
    private RideUpdateDto rideForUpdateDto;
    private RideReadDto expectedResult;
    private Long id;

    @BeforeEach
    void setUp() {
        ride = TestSetUps.getRide();
        id = TestSetUps.id;
        rideCreateDto = TestSetUps.getCreateDto();
        expectedResult = TestSetUps.getReadDto();
        expectedResultInUpdate = TestSetUps.getUpdateDto();
        updateRide = TestSetUps.getUpdateReview();
        rideForUpdateDto = TestSetUps.getRideUpdateDto();
    }

    @Test
    @DisplayName("find ride by id")
    void givenId_findById_returnUser() {
        when(rideRepository.findById(id))
                .thenReturn(Optional.of(ride));
        when(rideMapper.toRideReadDto(ride))
                .thenReturn(expectedResult);

        var actual = reviewService.findById(id);

        assertThat(actual)
                .isEqualTo(expectedResult);

        verify(rideMapper).toRideReadDto(ride);
        verify(rideRepository).findById(id);
    }

    @Test
    @DisplayName("ride not found exception")
    void givenId_findById_thrownRideNotFoundException() {
        when(rideRepository.findById(id))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() ->
                reviewService.findById(id)
        ).isInstanceOf(RideNotFoundException.class);

        verify(rideRepository).findById(id);
    }

    @Test
    @DisplayName("create ride")
    void givenRidePayload_create_returnCreatedUser() {
        when(rideRepository.save(ride))
                .thenReturn(ride);
        when(rideMapper.toRide(rideCreateDto))
                .thenReturn(ride);
        when(rideMapper.toRideReadDto(ride))
                .thenReturn(expectedResult);

        var actual = reviewService.create(rideCreateDto);

        assertThat(actual)
                .isEqualTo(expectedResult);

        verify(rideRepository).save(ride);
        verify(rideMapper).toRide(rideCreateDto);
        verify(rideMapper).toRideReadDto(ride);
    }

    @Test
    @DisplayName("update ride by id")
    void givenRideIdAndRideUpdatePayload_update_returnUpdatedUser() {
        when(rideRepository.findById(id))
                .thenReturn(Optional.of(ride));
        when(rideRepository.save(ride))
                .thenReturn(updateRide);
        when(rideMapper.toRideReadDto(updateRide))
                .thenReturn(expectedResultInUpdate);
        when(rideMapper.updateRide(ride, rideForUpdateDto))
                .thenReturn(updateRide);

        var ridDto = reviewService.update(id, rideForUpdateDto);

        assertThat(ridDto)
                .isEqualTo(expectedResultInUpdate);

        verify(rideRepository).findById(id);
        verify(rideRepository).save(ride);
        verify(rideMapper).toRideReadDto(updateRide);
        verify(rideMapper).updateRide(ride, rideForUpdateDto);
    }

    @Test
    @DisplayName("delete ride by id")
    void givenRideId_delete_nothingReturn() {
        doNothing().when(rideRepository).deleteById(id);

        reviewService.delete(id);

        verify(rideRepository).deleteById(id);
    }
}