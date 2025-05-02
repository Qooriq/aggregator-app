package com.java.akdev.driverservice.dto;

import lombok.Builder;

import java.io.Serializable;
import java.util.UUID;

@Builder
public record ReviewMessage(
        UUID userId,
        Double review
) implements Serializable {
}
