package com.java.akdev.passengerservice.enumeration;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ExceptionMessages {
    WRONG_TYPE("PassengerController.field.conversionNotSupported"),
    FIELD_MUST_PRESENT("PassengerController.field.mustPresent"),
    MUST_BE_POSITIVE("PassengerController.field.mustBePositive"),
    PASSENGER_NOT_FOUND("PassengerController.passenger.notFound");

    private final String name;
}
