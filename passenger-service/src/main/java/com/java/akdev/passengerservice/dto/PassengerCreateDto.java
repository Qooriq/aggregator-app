package com.java.akdev.passengerservice.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import lombok.Builder;

@Builder
public record PassengerCreateDto(
        @NotBlank(message = "PassengerController.firstName.notBlank")
        String firstName,
        @NotBlank(message = "PassengerController.lastName.notBlank")
        String lastName,
        @Email(message = "PassengerController.email")
        @NotBlank(message = "PassengerController.username.notBlank")
        String username,
        @NotBlank(message = "PassengerController.password.notBlank")
        String password,
        String phoneNumber
) {
}
