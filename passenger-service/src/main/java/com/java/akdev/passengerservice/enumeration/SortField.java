package com.java.akdev.passengerservice.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SortField {
    ID("id"),
    LAST_NAME("lastName"),
    FIRST_NAME("firstName"),
    USERNAME("username"),
    PASSWORD("password"),
    PHONE_NUMBER("phoneNumber");

    private final String name;
}
