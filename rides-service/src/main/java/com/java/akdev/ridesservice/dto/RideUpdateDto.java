package com.java.akdev.ridesservice.dto;

import com.java.akdev.ridesservice.enumeration.RideStatus;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

import java.time.Instant;
import java.util.UUID;

@Builder
public record RideUpdateDto(
        @NotBlank
        UUID passengerId,
        @NotBlank
        UUID driverId,
        Long passengerReviewDriver,
        Long driverReviewPassenger,
        @NotBlank
        String startLocation,
        @NotBlank
        String endLocation,
        @NotBlank
        Instant startTime,
        @NotBlank
        Instant endTime,
        @NotBlank
        Double ridePrice,
        RideStatus rideStatus
) {
}
