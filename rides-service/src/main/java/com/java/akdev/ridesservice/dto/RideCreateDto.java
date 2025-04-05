package com.java.akdev.ridesservice.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

import java.util.UUID;

@Builder
public record RideCreateDto(
        @NotBlank
        UUID passengerId,
        @NotBlank
        String startLocation,
        @NotBlank
        String endLocation
) {
}
