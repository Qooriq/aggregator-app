package com.java.akdev.ridesservice.util;

import com.java.akdev.ridesservice.dto.RideCreateDto;
import com.java.akdev.ridesservice.dto.RideReadDto;
import com.java.akdev.ridesservice.dto.RideUpdateDto;
import com.java.akdev.ridesservice.entity.Ride;
import lombok.experimental.UtilityClass;

import java.util.UUID;

@UtilityClass
public class TestSetUps {

    public static Long id = 1L;
    private final static UUID PASSENGER_ID = UUID.randomUUID();
    private final static UUID DRIVER_ID = UUID.randomUUID();
    private final static Long PASSENGER_REVIEW_DRIVER = 5L;
    private final static Long DRIVER_REVIEW_PASSENGER = 5L;
    private final static Double RIDE_PRICE = 12.0;
    private final static String START_LOCATION = "start street";
    private final static String END_LOCATION = "end street";
    private final static String NEW_END_LOCATION = "end street 2.0";
    private final static String DRIVER = "Vodila";

    public static Ride getRide() {
        return Ride.builder()
                .id(id)
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
                .id(id)
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
