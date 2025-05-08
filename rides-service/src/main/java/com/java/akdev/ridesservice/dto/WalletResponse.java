package com.java.akdev.ridesservice.dto;

import com.java.akdev.ridesservice.enumeration.OperationResult;
import lombok.Builder;

@Builder
public record WalletResponse(
        OperationResult message
) {
}
