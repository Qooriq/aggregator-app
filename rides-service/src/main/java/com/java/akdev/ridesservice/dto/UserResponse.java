package com.java.akdev.ridesservice.dto;

import lombok.Builder;

@Builder
public record UserResponse(
        String firstName,
        String lastName,
        String username
) {
}
