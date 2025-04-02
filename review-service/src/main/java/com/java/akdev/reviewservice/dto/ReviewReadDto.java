package com.java.akdev.reviewservice.dto;

public record ReviewReadDto(
    Short review,
    String receiver,
    String comment
) {
}
