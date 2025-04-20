package com.java.akdev.passengerservice.dto;

import lombok.Builder;

@Builder
public record ReviewResponse(
        String userId,
        Double review
) {
}
