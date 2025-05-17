package com.java.akdev.ridesservice.util;

import com.java.akdev.ridesservice.dto.RideCreateDto;
import com.java.akdev.ridesservice.dto.RideReadDto;
import com.java.akdev.ridesservice.dto.RideUpdateDto;
import com.java.akdev.ridesservice.entity.Ride;
import com.java.akdev.ridesservice.enumeration.Order;
import com.java.akdev.ridesservice.enumeration.SortField;
import lombok.experimental.UtilityClass;

import java.time.Instant;
import java.util.UUID;

@UtilityClass
public class TestSetUps {

    public static Long id = 1L;
    public static Long negativeId = -1L;
    public final static Integer DEFAULT_PAGE = 1;
    public final static Integer DEFAULT_PAGE_SIZE = 10;
    public final static SortField SORT_FIELD = SortField.ID;
    public final static Order ORDER = Order.ASC;
    public final static UUID PASSENGER_ID = UUID.fromString("4ebba608-6315-447e-9bf5-4e20da6fb0b0");
    public final static UUID DRIVER_ID = UUID.randomUUID();
    public final static Long PASSENGER_REVIEW_DRIVER = 5L;
    public final static Long DRIVER_REVIEW_PASSENGER = 5L;
    public final static Double RIDE_PRICE = 10.0;
    public final static String START_LOCATION = "minsk";
    public final static String END_LOCATION = "Lida";
    public final static String NEW_END_LOCATION = "Lida2.0";
    public final static String DRIVER = null;
    public final static String startEndpoint = "/api/v1/rides";
    public final static String startEndpointWithId = "/api/v1/rides/{id}";

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
                PASSENGER_ID, START_LOCATION, END_LOCATION, Instant.now()
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
