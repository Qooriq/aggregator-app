package com.java.akdev.walletservice.enumeration;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SortField {
    ID("id"),
    CARD_NUMBER("cardNumber"),
    AMOUNT("amount"),
    USER_ID("userId");

    private final String name;
}
