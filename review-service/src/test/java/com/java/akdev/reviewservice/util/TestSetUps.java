package com.java.akdev.reviewservice.util;

import com.java.akdev.reviewservice.dto.ReviewCreateDto;
import com.java.akdev.reviewservice.dto.ReviewReadDto;
import com.java.akdev.reviewservice.entity.Review;
import com.java.akdev.reviewservice.enumeration.Receiver;
import lombok.experimental.UtilityClass;

import java.util.UUID;

@UtilityClass
public class TestSetUps {

    public static Long id = 1L;
    private static Long rideId = 1L;
    private final static UUID PASSENGER_ID = UUID.randomUUID();
    private final static UUID DRIVER_ID = UUID.randomUUID();
    private final static Short REVIEW = 5;
    private final static String COMMENT = "ABOBA";
    private final static String NEW_COMMENT = "ABOBA_2.0";

    public static Review getReview() {
        return Review.builder()
                .id(id)
                .receiverId(PASSENGER_ID)
                .reviewerId(DRIVER_ID)
                .review(REVIEW)
                .comment(COMMENT)
                .rideId(rideId)
                .receiver(Receiver.PASSENGER)
                .build();
    }

    public static ReviewCreateDto getCreateDto() {
        return new ReviewCreateDto(
                PASSENGER_ID, DRIVER_ID, COMMENT, REVIEW, Receiver.PASSENGER, rideId
        );
    }

    public static Review getUpdateReview() {
        return Review.builder()
                .id(id)
                .receiverId(PASSENGER_ID)
                .reviewerId(DRIVER_ID)
                .review(REVIEW)
                .comment(NEW_COMMENT)
                .rideId(rideId)
                .receiver(Receiver.PASSENGER)
                .build();
    }

    public static ReviewReadDto getUpdateDto() {
        return new ReviewReadDto(REVIEW, Receiver.PASSENGER, NEW_COMMENT);
    }

    public static ReviewReadDto getReadDto() {
        return new ReviewReadDto(REVIEW, Receiver.PASSENGER, COMMENT);
    }
}
