package com.java.akdev.ridesservice.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RideType {
    ECONOMY(1.0), COMFORT(1.2), ELECTRIC(1.3);

    private final Double coefficient;

}
