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
import static org.mockito.Mockito.*;

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
    private RideReadDto rideUpdateDto;
    private RideUpdateDto rideForUpdateDto;
    private RideReadDto rideReadDto;
    private Long id;

    @BeforeEach
    void setUp() {
        ride = TestSetUps.getRide();
        id = TestSetUps.id;
        rideCreateDto = TestSetUps.getCreateDto();
        rideReadDto = TestSetUps.getReadDto();
        rideUpdateDto = TestSetUps.getUpdateDto();
        updateRide = TestSetUps.getUpdateReview();
        rideForUpdateDto = TestSetUps.getRideUpdateDto();
    }

    @Test
    @DisplayName("find ride by id")
    void findRideById() {

        when(rideRepository.findById(id))
                .thenReturn(Optional.of(ride));
        when(rideMapper.toRideReadDto(ride))
                .thenReturn(rideReadDto);

        var rev = reviewService.findById(id);

        assertThat(rev)
                .isEqualTo(rideReadDto);

        verify(rideMapper).toRideReadDto(ride);
        verify(rideRepository).findById(id);
    }

    @Test
    @DisplayName("ride not found exception")
    void findRideNotFoundById() {

        when(rideRepository.findById(id))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() ->
                reviewService.findById(id)
        ).isInstanceOf(RideNotFoundException.class);

        verify(rideRepository).findById(id);
    }

    @Test
    @DisplayName("create ride")
    void createRide() {
        when(rideRepository.save(ride))
                .thenReturn(ride);
        when(rideMapper.toRide(rideCreateDto))
                .thenReturn(ride);
        when(rideMapper.toRideReadDto(ride))
                .thenReturn(rideReadDto);

        var rid = reviewService.create(rideCreateDto);

        assertThat(rid)
                .isEqualTo(rideReadDto);

        verify(rideRepository).save(ride);
        verify(rideMapper).toRide(rideCreateDto);
        verify(rideMapper).toRideReadDto(ride);
    }

    @Test
    @DisplayName("update ride by id")
    void update() {
        when(rideRepository.findById(id))
                .thenReturn(Optional.of(ride));
        when(rideRepository.save(ride))
                .thenReturn(updateRide);
        when(rideMapper.toRideReadDto(updateRide))
                .thenReturn(rideUpdateDto);
        when(rideMapper.updateRide(ride, rideForUpdateDto))
                .thenReturn(updateRide);

        var ridDto = reviewService.update(id, rideForUpdateDto);

        assertThat(ridDto)
                .isEqualTo(rideUpdateDto);

        verify(rideRepository).findById(id);
        verify(rideRepository).save(ride);
        verify(rideMapper).toRideReadDto(updateRide);
        verify(rideMapper).updateRide(ride, rideForUpdateDto);
    }

    @Test
    @DisplayName("delete ride by id")
    void deleteRide() {
        doNothing().when(rideRepository).deleteById(id);

        reviewService.delete(id);

        verify(rideRepository).deleteById(id);
    }
}