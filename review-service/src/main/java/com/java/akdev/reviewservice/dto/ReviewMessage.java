package com.java.akdev.reviewservice.dto;

import lombok.Builder;

@Builder
public record ReviewMessage(
        String userId,
        Double review
) {
}
