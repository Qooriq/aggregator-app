package com.java.akdev.passengerservice.dto;

import java.util.UUID;

public record PassengerRatingDto(
        UUID passengerId,
        UUID driverId,
        Long review
) {
}
