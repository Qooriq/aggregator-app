package com.java.akdev.ridesservice.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

import java.util.UUID;

@Builder
public record RideCreateDto(
        @NotBlank(message = "argumentNotValid.passengerId.notBlank")
        UUID passengerId,
        @NotBlank(message = "argumentNotValid.startLocation.notBlank")
        String startLocation,
        @NotBlank(message = "argumentNotValid.endLocation.notBlank")
        String endLocation
) {
}
