package com.java.akdev.driverservice.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record DriverCreateDto(
        @NotBlank(message = "DriverController.field.notBlank")
        String firstName,
        @NotBlank(message = "DriverController.field.notBlank")
        String lastName,
        @Email(message = "DriverController.email")
        @NotBlank(message = "DriverController.field.notBlank")
        String username,
        @NotBlank(message = "DriverController.field.notBlank")
        String password,
        String phoneNumber
) {
}
