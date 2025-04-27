package com.java.akdev.driverservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.io.Serializable;
import java.util.UUID;

@Builder
public record ReviewMessage(
        @JsonProperty("userId") UUID userId,
        @JsonProperty("review") Double review
) implements Serializable {
}
