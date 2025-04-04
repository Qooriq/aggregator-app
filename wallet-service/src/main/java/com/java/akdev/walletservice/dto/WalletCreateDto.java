package com.java.akdev.walletservice.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

import java.util.UUID;

@Builder
public record WalletCreateDto(
        @NotBlank
        String cardNumber,
        @NotBlank
        Double amount,
        @NotBlank
        UUID userId
) {
}
