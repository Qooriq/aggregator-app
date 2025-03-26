package com.java.akdev.passengerservice.dto;

public record PassengerCreateDto(
        String firstName,
        String lastName,
        String username,
        String password,
        String phoneNumber
) {
}
