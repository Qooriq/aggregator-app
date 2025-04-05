package com.java.akdev.reviewservice.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SortField {
    ID("id"),
    PASSENGER_ID("passengerId"),
    DRIVER_ID("driverId"),
    COMMENT("comment"),
    REVIEW("review"),
    RIDE_ID("rideId");

    private final String name;
}
