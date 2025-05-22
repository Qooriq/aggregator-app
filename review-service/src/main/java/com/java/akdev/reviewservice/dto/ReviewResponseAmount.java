package com.java.akdev.reviewservice.dto;

import lombok.Builder;

@Builder
public record ReviewResponseAmount(
        Double review
) {
}
