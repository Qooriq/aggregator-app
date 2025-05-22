package com.java.akdev.reviewservice.dto;

import com.java.akdev.reviewservice.enumeration.Receiver;

public record ReviewReadDto(
    Short review,
    Receiver receiver,
    String comment
) {
}
