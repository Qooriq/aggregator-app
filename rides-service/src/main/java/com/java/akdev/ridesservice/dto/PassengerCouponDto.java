package com.java.akdev.ridesservice.dto;

import java.util.UUID;

public record PassengerCouponDto(
        String couponName,
        UUID passengerId
) {
}
