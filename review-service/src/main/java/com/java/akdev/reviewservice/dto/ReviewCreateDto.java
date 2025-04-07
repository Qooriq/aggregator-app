package com.java.akdev.reviewservice.dto;

import com.java.akdev.reviewservice.enumeration.Receiver;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.util.UUID;

@Builder
public record ReviewCreateDto(
        @NotNull(message = "ReviewController.receiverId.notBlank")
        UUID receiverId,
        @NotNull(message = "ReviewController.reviewerId.notBlank")
        UUID reviewerId,
        String comment,
        @NotNull(message = "ReviewController.review.notBlank")
        Short review,
        @NotNull(message = "ReviewController.receiver.notBlank")
        Receiver receiver,
        @NotNull(message = "ReviewController.rideId.notBlank")
        Long rideId
) {
}
