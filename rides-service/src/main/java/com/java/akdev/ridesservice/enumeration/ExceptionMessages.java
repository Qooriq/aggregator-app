package com.java.akdev.ridesservice.enumeration;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ExceptionMessages {
    WRONG_TYPE("RideController.field.conversionNotSupported"),
    FIELD_MUST_PRESENT("RideController.field.mustPresent"),
    MUST_BE_POSITIVE("RideController.field.mustBePositive");

    private final String name;
}
