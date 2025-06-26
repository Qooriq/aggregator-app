package com.java.akdev.ridesservice.unit;

import com.java.akdev.commonmodels.dto.ReviewResponse;
import com.java.akdev.commonmodels.dto.RideResponse;
import com.java.akdev.commonmodels.dto.UserResponse;
import com.java.akdev.commonmodels.enumeration.Receiver;
import com.java.akdev.ridesservice.client.CheckDriverExistClient;
import com.java.akdev.ridesservice.client.CheckPassengerExistClient;
import com.java.akdev.ridesservice.client.CheckReviewExistClient;
import com.java.akdev.ridesservice.dto.RideCreateDto;
import com.java.akdev.ridesservice.dto.RideUpdateDto;
import com.java.akdev.ridesservice.entity.Ride;
import com.java.akdev.ridesservice.exception.EntityNotFound;
import com.java.akdev.ridesservice.mapper.RideMapper;
import com.java.akdev.ridesservice.repository.CouponRepository;
import com.java.akdev.ridesservice.repository.PassengerCouponRepository;
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
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RideServiceImplTest {

    @Mock
    private RideMapper rideMapper;
    @Mock
    private RideRepository rideRepository;
    @Mock
    private CheckPassengerExistClient checkPassengerExistClient;
    @Mock
    private CheckDriverExistClient checkDriverExistClient;
    @Mock
    private CheckReviewExistClient checkReviewExistClient;
    @Mock
    private CouponRepository couponRepository;
    @Mock
    private PassengerCouponRepository passengerCouponRepository;
    @InjectMocks
    private RideServiceImpl reviewService;

    private Ride ride;
    private Ride updateRide;
    private RideCreateDto rideCreateDto;
    private RideResponse expectedResultInUpdate;
    private RideUpdateDto rideForUpdateDto;
    private RideResponse expectedResult;
    private Long id;

    @BeforeEach
    void setUp() {
        ride = TestSetUps.getRide();
        id = TestSetUps.ID;
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
        when(rideMapper.toRideResponse(ride))
                .thenReturn(expectedResult);

        var actual = reviewService.findById(id);

        assertThat(actual)
                .isEqualTo(expectedResult);

        verify(rideMapper).toRideResponse(ride);
        verify(rideRepository).findById(id);
    }

    @Test
    @DisplayName("ride not found exception")
    void givenId_findById_thrownRideNotFoundException() {
        when(rideRepository.findById(id))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() ->
                reviewService.findById(id)
        ).isInstanceOf(EntityNotFound.class);

        verify(rideRepository).findById(id);
    }

    @Test
    @DisplayName("create ride")
    void givenRidePayload_create_returnCreatedUser() {
        when(rideRepository.save(ride))
                .thenReturn(ride);
        when(rideMapper.toRide(rideCreateDto))
                .thenReturn(ride);
        when(rideMapper.toRideResponse(ride))
                .thenReturn(expectedResult);
        when(checkPassengerExistClient.findPassengerById(any()))
                .thenReturn(ResponseEntity.ok(new UserResponse("a", "a", "a")));
        when(couponRepository.existsByCoupon(anyString()))
                .thenReturn(false);

        var actual = reviewService.create(rideCreateDto, " ");

        assertThat(actual)
                .isEqualTo(expectedResult);

        verify(rideRepository).save(ride);
        verify(rideMapper).toRide(rideCreateDto);
        verify(rideMapper).toRideResponse(ride);
        verify(checkPassengerExistClient).findPassengerById(any());
        verify(couponRepository).existsByCoupon(anyString());
    }

    @Test
    @DisplayName("update ride by id")
    void givenRideIdAndRideUpdatePayload_update_returnUpdatedUser() {
        when(rideRepository.findById(id))
                .thenReturn(Optional.of(ride));
        when(rideRepository.save(ride))
                .thenReturn(updateRide);
        when(rideMapper.toRideResponse(updateRide))
                .thenReturn(expectedResultInUpdate);
        when(rideMapper.updateRide(ride, rideForUpdateDto))
                .thenReturn(updateRide);
        when(checkPassengerExistClient.findPassengerById(any()))
                .thenReturn(ResponseEntity.ok(new UserResponse("a", "a", "a")));
        when(checkDriverExistClient.findDriverById(any()))
                .thenReturn(ResponseEntity.ok(new UserResponse("a", "a", "a")));
        when(checkReviewExistClient.findReviewById(anyLong()))
                .thenReturn(ResponseEntity.ok(new ReviewResponse((short) 4, Receiver.PASSENGER, "a")));

        var ridDto = reviewService.update(id, rideForUpdateDto);

        assertThat(ridDto)
                .isEqualTo(expectedResultInUpdate);

        verify(rideRepository).findById(id);
        verify(rideRepository).save(ride);
        verify(rideMapper).toRideResponse(updateRide);
        verify(rideMapper).updateRide(ride, rideForUpdateDto);
        verify(checkPassengerExistClient).findPassengerById(any());
        verify(checkDriverExistClient).findDriverById(any());
        verify(checkReviewExistClient, times(2)).findReviewById(anyLong());
    }

    @Test
    @DisplayName("delete ride by id")
    void givenRideId_delete_nothingReturn() {
        doNothing().when(rideRepository).deleteById(id);

        reviewService.delete(id);

        verify(rideRepository).deleteById(id);
    }
}