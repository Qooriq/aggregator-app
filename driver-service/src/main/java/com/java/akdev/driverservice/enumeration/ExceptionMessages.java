package com.java.akdev.driverservice.enumeration;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ExceptionMessages {
    WRONG_TYPE("DriverController.field.conversionNotSupported"),
    FIELD_MUST_PRESENT("DriverController.field.mustPresent"),
    MUST_BE_POSITIVE("DriverController.field.mustBePositive");

    private final String name;
}
