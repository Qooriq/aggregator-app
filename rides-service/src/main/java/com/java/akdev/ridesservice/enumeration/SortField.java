package com.java.akdev.ridesservice.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SortField {
    ID("id"),
    CARD_NUMBER("cardNumber"),
    AMOUNT("amount"),
    USER_ID("userId");

    private final String name;
}
