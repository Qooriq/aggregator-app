package com.java.akdev.ridesservice.dto;

import lombok.Builder;

@Builder
public record ErrorResponse(
        String error
) {
}
