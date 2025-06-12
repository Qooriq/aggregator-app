package com.java.akdev.commonmodels.dto;

import com.java.akdev.commonmodels.enumeration.Receiver;

public record ReviewResponse(
        Short review,
        Receiver receiver,
        String comment
) {
}
