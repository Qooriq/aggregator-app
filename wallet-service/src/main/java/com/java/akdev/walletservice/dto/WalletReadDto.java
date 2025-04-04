package com.java.akdev.walletservice.dto;

import lombok.Builder;

@Builder
public record WalletReadDto(
        String cardNumber,
        Double amount
) {
}
