package com.java.akdev.passengerservice.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import lombok.Builder;

@Builder
public record PassengerCreateDto(
        @NotBlank(message = "PassengerController.notBlank")
        String firstName,
        @NotBlank(message = "PassengerController.notBlank")
        String lastName,
        @Email(message = "PassengerController.email")
        @NotBlank(message = "PassengerController.notBlank")
        String username,
        @NotBlank(message = "PassengerController.notBlank")
        String password,
        String phoneNumber
) {
}
