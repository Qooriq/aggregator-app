package com.java.akdev.commonmodels.dto;

public record ReviewResponse(
        Short review,
        String receiver,
        String comment
) {
}
