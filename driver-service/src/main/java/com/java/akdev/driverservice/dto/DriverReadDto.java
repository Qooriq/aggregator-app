package com.java.akdev.driverservice.dto;

import lombok.Builder;

@Builder
public record DriverReadDto(
        String firstName,
        String lastName,
        String username
) {
}
