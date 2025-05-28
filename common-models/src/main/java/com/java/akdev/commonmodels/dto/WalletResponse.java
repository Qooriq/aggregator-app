package com.java.akdev.commonmodels.dto;

import com.java.akdev.commonmodels.enumeration.OperationResult;

public record WalletResponse(
        OperationResult message
) {
}
