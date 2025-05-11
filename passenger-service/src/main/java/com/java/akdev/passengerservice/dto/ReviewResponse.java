package com.java.akdev.passengerservice.dto;

import lombok.Builder;

import java.util.UUID;

@Builder
public record ReviewResponse(
        UUID userId,
        Double review
) {
}
