package com.java.akdev.driverservice.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record DriverCreateDto(
        @NotBlank(message = "DriverController.firstName.notBlank")
        String firstName,
        @NotBlank(message = "DriverController.lastName.notBlank")
        String lastName,
        @Email(message = "DriverController.email")
        @NotBlank(message = "DriverController.username.notBlank")
        String username,
        @NotBlank(message = "DriverController.password.notBlank")
        String password,
        String phoneNumber
) {
}
