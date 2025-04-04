package com.java.akdev.walletservice.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record WalletReadDto(
        @NotBlank
        String cardNumber,
        @NotBlank
        Double amount
) {
}
