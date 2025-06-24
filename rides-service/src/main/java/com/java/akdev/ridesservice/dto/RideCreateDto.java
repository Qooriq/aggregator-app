package com.java.akdev.ridesservice.dto;

import com.java.akdev.ridesservice.enumeration.PaymentMethod;
import com.java.akdev.ridesservice.enumeration.RideType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.util.UUID;

@Builder
public record RideCreateDto(
        @NotBlank(message = "argumentNotValid.passengerId.notBlank")
        UUID passengerId,
        @NotBlank(message = "argumentNotValid.startLocation.notBlank")
        PointDto startLocation,
        @NotBlank(message = "argumentNotValid.endLocation.notBlank")
        PointDto endLocation,
        @NotNull(message = "argumentNotValid.paymentMethod.notNull")
        PaymentMethod paymentMethod,
        @NotNull(message = "argumentNotValid.rideType.notNull")
        RideType rideType
) {
}
