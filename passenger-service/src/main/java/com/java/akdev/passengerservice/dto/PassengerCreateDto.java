package com.java.akdev.passengerservice.dto;

import lombok.Builder;

@Builder
public record PassengerCreateDto(
        String firstName,
        String lastName,
        String username,
        String password,
        String phoneNumber
) {
}
