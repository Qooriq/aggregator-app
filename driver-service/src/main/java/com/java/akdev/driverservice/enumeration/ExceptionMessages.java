package com.java.akdev.driverservice.enumeration;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ExceptionMessages {
    WRONG_TYPE("DriverController.field.conversionNotSupported"),
    FIELD_MUST_PRESENT("DriverController.field.mustPresent"),
    MUST_BE_POSITIVE("DriverController.field.mustBePositive"),
    DRIVER_NOT_FOUND("DiverController.passenger.notFound"),
    ALREADY_EXISTS("DriverController.field.alreadyExists");

    private final String name;
}
