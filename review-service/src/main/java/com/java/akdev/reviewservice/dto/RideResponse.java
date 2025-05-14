package com.java.akdev.reviewservice.dto;

import lombok.Builder;

@Builder
public record RideResponse(
        String startLocation,
        String endLocation,
        Double ridePrice,
        String driver
) {
}
