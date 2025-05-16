package com.java.akdev.reviewservice.util;

import com.java.akdev.reviewservice.dto.ReviewCreateDto;
import com.java.akdev.reviewservice.dto.ReviewReadDto;
import com.java.akdev.reviewservice.entity.Review;
import com.java.akdev.reviewservice.enumeration.Order;
import com.java.akdev.reviewservice.enumeration.Receiver;
import com.java.akdev.reviewservice.enumeration.SortField;
import lombok.experimental.UtilityClass;

import java.util.UUID;

@UtilityClass
public class TestSetUps {

    public static Long id = 1L;
    public final static Integer DEFAULT_PAGE_SIZE = 10;
    public final static Integer DEFAULT_PAGE = 1;
    public final static SortField SORT_FIELD = SortField.ID;
    public final static Order ORDER = Order.ASC;
    private final static Long rideId = 1L;
    private final static UUID PASSENGER_ID = UUID.fromString("82582967-375f-4188-b3fc-e0390b9f22b4");
    private final static UUID DRIVER_ID = UUID.fromString("4ebba608-6315-447e-9bf5-4e20da6fb0b0");
    private final static Short REVIEW = 5;
    private final static String COMMENT = "ha";
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
                PASSENGER_ID, DRIVER_ID, NEW_COMMENT, REVIEW, Receiver.PASSENGER, rideId
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
