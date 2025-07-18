package com.java.akdev.ridesservice.util;

import com.java.akdev.commonmodels.dto.RideResponse;
import com.java.akdev.ridesservice.dto.PointDto;
import com.java.akdev.ridesservice.dto.RideCreateDto;
import com.java.akdev.ridesservice.dto.RideUpdateDto;
import com.java.akdev.ridesservice.entity.Ride;
import com.java.akdev.ridesservice.enumeration.Order;
import com.java.akdev.ridesservice.enumeration.PaymentMethod;
import com.java.akdev.ridesservice.enumeration.RideType;
import com.java.akdev.ridesservice.enumeration.SortField;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class TestSetUps {

    public static final Long ID = 1L;
    public static final Long NEGATIVE_ID = -1L;
    public static final Integer DEFAULT_PAGE = 1;
    public static final Integer DEFAULT_PAGE_SIZE = 10;
    public static final SortField SORT_FIELD = SortField.ID;
    public static final Order ORDER = Order.ASC;
    public static final UUID PASSENGER_ID = UUID.fromString("4ebba608-6315-447e-9bf5-4e20da6fb0b0");
    public static final UUID DRIVER_ID = UUID.fromString("1826829b-d77a-4908-b1b4-94cf5346a038");
    public static final Long PASSENGER_REVIEW_DRIVER = 5L;
    public static final Long DRIVER_REVIEW_PASSENGER = 5L;
    public static final Double RIDE_PRICE = 79.95536530359746;
    public static final PointDto START_LOCATION = new PointDto(5.4, 5.3);
    public static final PointDto END_LOCATION = new PointDto(5.0, 5.9);
    public static final PointDto NEW_END_LOCATION = new PointDto(5.8, 4.3);
    public static final String DRIVER = null;
    public static final String RIDE_BASE_URL = "/api/v1/rides";
    public static final String RIDE_BASE_URL_WITH_ID = "/api/v1/rides/{id}";

    public static Ride getRide() {
        return Ride.builder()
                .id(ID)
                .passengerId(PASSENGER_ID)
                .driverId(DRIVER_ID)
                .passengerReviewDriver(PASSENGER_REVIEW_DRIVER)
                .driverReviewPassenger(DRIVER_REVIEW_PASSENGER)
                .ridePrice(RIDE_PRICE)
                .endLocation(END_LOCATION.toString())
                .rideType(RideType.ECONOMY)
                .build();
    }

    public static RideCreateDto getCreateDto() {
        return new RideCreateDto(
                PASSENGER_ID, START_LOCATION, END_LOCATION, PaymentMethod.CARD, RideType.ECONOMY
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
                .startTime(Instant.now())
                .endLocation(NEW_END_LOCATION.toString())
                .rideType(RideType.ECONOMY)
                .build();
    }

    public static RideResponse getUpdateDto() {
        return new RideResponse(START_LOCATION.toString(), NEW_END_LOCATION.toString(), RIDE_PRICE, DRIVER);
    }

    public static RideResponse getReadDto() {
        return new RideResponse(START_LOCATION.toString(), END_LOCATION.toString(), RIDE_PRICE, DRIVER);
    }

    public static RideUpdateDto getRideUpdateDto() {
        return new RideUpdateDto(PASSENGER_ID, DRIVER_ID,
                3L, 2L,
                START_LOCATION.toString(), NEW_END_LOCATION.toString(), Instant.now(), null,
                RIDE_PRICE, null);
    }
}
