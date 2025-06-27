package com.java.akdev.commonmodels.dto;

import java.util.UUID;

public record UserRegistrationCreateDto(
        UUID id,
        String firstName,
        String lastName,
        String username,
        String password,
        String phoneNumber
) {
}
