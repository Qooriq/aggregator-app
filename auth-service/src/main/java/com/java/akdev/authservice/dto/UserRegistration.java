package com.java.akdev.authservice.dto;

public record UserRegistration(
        String firstName,
        String lastName,
        String username,
        String password,
        String phoneNumber
) {
}
