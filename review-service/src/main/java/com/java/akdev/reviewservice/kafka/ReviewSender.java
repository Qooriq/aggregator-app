package com.java.akdev.reviewservice.kafka;

import com.java.akdev.reviewservice.dto.ReviewMessage;

public interface ReviewSender {

    void sendPassengerReview(ReviewMessage review);

    void sendDriverReview(ReviewMessage review);
}
