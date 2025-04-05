package com.java.akdev.driverservice.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SortField {
    ID("id"),
    FIRST_NAME("firstName"),
    LAST_NAME("lastName"),
    USERNAME("username"),
    PASSWORD("password"),
    PHONE_NUMBER("phoneNumber");

    private final String name;
}
