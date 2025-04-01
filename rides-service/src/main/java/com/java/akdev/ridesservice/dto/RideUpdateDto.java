package com.java.akdev.ridesservice.dto;

import com.java.akdev.ridesservice.enumeration.RideStatus;
import lombok.Builder;

import java.time.Instant;
import java.util.UUID;

@Builder
public record RideUpdateDto(
        UUID passengerId,
        UUID driverId,
        Long passengerReviewDriver,
        Long driverReviewPassenger,
        String startLocation,
        String endLocation,
        Instant startTime,
        Instant endTime,
        Double ridePrice,
        RideStatus rideStatus
) {
}
