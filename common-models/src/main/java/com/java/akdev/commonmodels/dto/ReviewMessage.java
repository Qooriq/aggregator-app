package com.java.akdev.commonmodels.dto;

import java.io.Serializable;
import java.util.UUID;

public record ReviewMessage(
        UUID userId,
        Double review
) implements Serializable {
}
