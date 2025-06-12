package com.java.akdev.reviewservice.dto;

import com.java.akdev.commonmodels.enumeration.Receiver;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.util.UUID;

@Builder
public record ReviewCreateDto(
        @NotNull(message = "ReviewController.field.notNull")
        UUID receiverId,
        @NotNull(message = "ReviewController.field.notNull")
        UUID reviewerId,
        String comment,
        @NotNull(message = "ReviewController.field.notNull")
        Short review,
        @NotNull(message = "ReviewController.field.notNull")
        Receiver receiver,
        @NotNull(message = "ReviewController.field.notNull")
        Long rideId
) {
}
