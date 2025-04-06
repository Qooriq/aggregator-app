package com.java.akdev.walletservice.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

import java.util.UUID;

@Builder
public record WalletCreateDto(
        @NotBlank(message = "WalletController.cardNumber.notBlank")
        String cardNumber,
        @NotBlank(message = "WalletController.amount.notBlank")
        Double amount,
        @NotBlank(message = "WalletController.userId.notBlank")
        UUID userId
) {
}
