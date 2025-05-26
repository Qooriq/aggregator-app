package com.java.akdev.walletservice.dto;

import lombok.Builder;

@Builder
public record UserReadDto(
        String firstName,
        String lastName,
        String username
) {
}
