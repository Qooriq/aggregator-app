package com.java.akdev.authservice.dto;

public record TokenResponse(
        String jwtToken,
        String refreshToken
) {
}
