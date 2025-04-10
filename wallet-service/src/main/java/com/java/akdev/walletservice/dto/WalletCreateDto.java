package com.java.akdev.walletservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.util.UUID;

@Builder
public record WalletCreateDto(
        @NotBlank(message = "WalletController.cardNumber.notBlank")
        String cardNumber,
        @NotNull(message = "WalletController.amount.notNull")
        Double amount,
        @NotNull(message = "WalletController.userId.notNull")
        UUID userId
) {
}
