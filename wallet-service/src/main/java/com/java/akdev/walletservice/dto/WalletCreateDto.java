package com.java.akdev.walletservice.dto;

import com.java.akdev.walletservice.enumeration.WalletOwner;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.util.UUID;

@Builder
public record WalletCreateDto(
        @NotBlank(message = "WalletController.field.notBlank")
        @NotNull(message = "WalletController.field.notNull")
        String cardNumber,
        @NotNull(message = "WalletController.field.notNull")
        Double amount,
        @NotNull(message = "WalletController.field.notNull")
        UUID userId,
        @NotNull(message = "WalletController.field.notNull")
        WalletOwner walletOwner
) {
}
