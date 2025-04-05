package com.java.akdev.ridesservice.dto;

import lombok.Builder;

@Builder
public record RideReadDto(
    String startLocation,
    String endLocation,
    Double ridePrice,
    String driver
) {
}
