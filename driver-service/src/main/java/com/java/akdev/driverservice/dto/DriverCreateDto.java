package com.java.akdev.driverservice.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record DriverCreateDto(
        @NotBlank
        String firstName,
        @NotBlank
        String lastName,
        @Email
        @NotBlank
        String username,
        @NotBlank
        String password,
        String phoneNumber
) {
}
