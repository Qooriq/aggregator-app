package com.java.akdev.reviewservice.dto;

import com.java.akdev.reviewservice.enumeration.Receiver;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.util.UUID;

@Builder
public record ReviewCreateDto(
        @NotNull(message = "ReviewController.receiverId.notNull")
        UUID receiverId,
        @NotNull(message = "ReviewController.reviewerId.notNull")
        UUID reviewerId,
        String comment,
        @NotNull(message = "ReviewController.review.notNull")
        Short review,
        @NotNull(message = "ReviewController.receiver.notNull")
        Receiver receiver,
        @NotNull(message = "ReviewController.rideId.notNull")
        Long rideId
) {
}
