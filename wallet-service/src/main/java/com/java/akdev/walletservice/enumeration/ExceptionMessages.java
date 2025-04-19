package com.java.akdev.walletservice.enumeration;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ExceptionMessages {
    WRONG_TYPE("WalletController.field.conversionNotSupported"),
    FIELD_MUST_PRESENT("WalletController.field.mustPresent"),
    MUST_BE_POSITIVE("WalletController.field.mustBePositive");

    private final String name;
}
