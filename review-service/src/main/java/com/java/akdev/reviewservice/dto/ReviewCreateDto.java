package com.java.akdev.reviewservice.dto;

import com.java.akdev.reviewservice.enumeration.Receiver;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

import java.util.UUID;

@Builder
public record ReviewCreateDto(
        @NotBlank(message = "ReviewController.passengerId.notBlank")
        UUID passengerId,
        @NotBlank(message = "ReviewController.driverId.notBlank")
        UUID driverId,
        String comment,
        @NotBlank(message = "ReviewController.review.notBlank")
        Short review,
        @NotBlank(message = "ReviewController.receiver.notBlank")
        Receiver receiver,
        @NotBlank(message = "ReviewController.rideId.notBlank")
        Long rideId
) {
}
