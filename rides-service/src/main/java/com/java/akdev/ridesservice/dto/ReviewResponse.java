package com.java.akdev.ridesservice.dto;

import lombok.Builder;

@Builder
public record ReviewResponse(
        Short review,
        String receiver,
        String comment
) {
}
