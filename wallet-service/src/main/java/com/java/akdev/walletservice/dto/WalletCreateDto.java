package com.java.akdev.walletservice.dto;

import lombok.Builder;

import java.util.UUID;

@Builder
public record WalletCreateDto(
        String cardNumber,
        Double amount,
        UUID userId
) {
}
