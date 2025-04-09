package com.java.akdev.ridesservice.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SortField {
    ID("id"),
    PASSENGER_ID("passengerId"),
    DRiVER_ID("driverId"),
    DRIVER_REVIEW_PASSENGER("userId"),
    PASSENGER_REVIEW_DRIVER("passengerReviewDriver"),
    START_LOCATION("startLocation"),
    END_LOCATION("endLocation"),
    START_TIME("startTime"),
    END_TIME("endTime"),
    RIDE_PRICE("ridePrice");

    private final String name;
}
