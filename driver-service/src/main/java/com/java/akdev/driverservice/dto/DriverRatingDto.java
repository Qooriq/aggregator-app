package com.java.akdev.driverservice.dto;

import lombok.Builder;

import java.util.UUID;

@Builder
public record DriverRatingDto(
        UUID passengerId,
        UUID driverId,
        Long review
) {
}
