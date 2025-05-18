package com.java.akdev.ridesservice.util;

import com.java.akdev.ridesservice.dto.RideCreateDto;
import com.java.akdev.ridesservice.dto.RideReadDto;
import com.java.akdev.ridesservice.dto.RideUpdateDto;
import com.java.akdev.ridesservice.entity.Ride;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class TestSetUps {

    public static final Long ID = 1L;
    public static final Long NEGATIVE_ID = -1L;
    public static final UUID PASSENGER_ID = UUID.fromString("4ebba608-6315-447e-9bf5-4e20da6fb0b0");
    public static final UUID DRIVER_ID = UUID.fromString("1826829b-d77a-4908-b1b4-94cf5346a038");
    public static final Long PASSENGER_REVIEW_DRIVER = 5L;
    public static final Long DRIVER_REVIEW_PASSENGER = 5L;
    public static final Double RIDE_PRICE = 12.0;
    public static final String START_LOCATION = "minsk";
    public static final String END_LOCATION = "Lida";
    public static final String NEW_END_LOCATION = "Lida2.0";
    public static final String DRIVER = null;

    public static Ride getRide() {
        return Ride.builder()
                .id(ID)
                .passengerId(PASSENGER_ID)
                .driverId(DRIVER_ID)
                .passengerReviewDriver(PASSENGER_REVIEW_DRIVER)
                .driverReviewPassenger(DRIVER_REVIEW_PASSENGER)
                .ridePrice(RIDE_PRICE)
                .endLocation(END_LOCATION)
                .build();
    }

    public static RideCreateDto getCreateDto() {
        return new RideCreateDto(
                PASSENGER_ID, START_LOCATION, END_LOCATION
        );
    }

    public static Ride getUpdateReview() {
        return Ride.builder()
                .id(ID)
                .passengerId(PASSENGER_ID)
                .driverId(DRIVER_ID)
                .passengerReviewDriver(PASSENGER_REVIEW_DRIVER)
                .driverReviewPassenger(DRIVER_REVIEW_PASSENGER)
                .ridePrice(RIDE_PRICE)
                .endLocation(NEW_END_LOCATION)
                .build();
    }

    public static RideReadDto getUpdateDto() {
        return new RideReadDto(START_LOCATION, NEW_END_LOCATION, RIDE_PRICE, DRIVER);
    }

    public static RideReadDto getReadDto() {
        return new RideReadDto(START_LOCATION, END_LOCATION, RIDE_PRICE, DRIVER);
    }

    public static RideUpdateDto getRideUpdateDto() {
        return new RideUpdateDto(PASSENGER_ID, DRIVER_ID,
                null, null,
                START_LOCATION, NEW_END_LOCATION, null, null,
                RIDE_PRICE, null);
    }
}
