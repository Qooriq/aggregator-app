package com.java.akdev.reviewservice.enumeration;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ExceptionMessages {
    WRONG_TYPE("ReviewController.field.conversionNotSupported"),
    FIELD_MUST_PRESENT("ReviewController.field.mustPresent"),
    MUST_BE_POSITIVE("ReviewController.field.mustBePositive");

    private final String name;
}

