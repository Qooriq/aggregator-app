package com.java.akdev.passengerservice.dto;

import lombok.Builder;

@Builder
public record PassengerReadDto(
        String firstName,
        String lastName,
        String username
) {
}
