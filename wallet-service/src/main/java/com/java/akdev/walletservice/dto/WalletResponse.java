package com.java.akdev.walletservice.dto;

import com.java.akdev.walletservice.enumeration.OperationResult;
import lombok.Builder;

@Builder
public record WalletResponse(
        OperationResult message
) {
}
