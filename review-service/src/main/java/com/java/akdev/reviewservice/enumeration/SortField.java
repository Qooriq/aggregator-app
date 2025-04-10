package com.java.akdev.reviewservice.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SortField {
    ID("id"),
    RECEIVER_ID("receiverId"),
    REVIEWER_ID("reviewerId"),
    COMMENT("comment"),
    REVIEW("review"),
    RIDE_ID("rideId");

    private final String name;
}
