package com.java.akdev.reviewservice.dto;

import com.java.akdev.reviewservice.enumeration.Receiver;
import lombok.Builder;

import java.util.UUID;

@Builder
public record ReviewCreateDto(
        UUID passengerId,
        UUID driverId,
        String comment,
        Short review,
        Receiver receiver,
        Long rideId
) {
}
