package com.java.akdev.commonmodels.dto;

public record RideResponse(
        String startLocation,
        String endLocation,
        Double ridePrice,
        String driver
) {
}
