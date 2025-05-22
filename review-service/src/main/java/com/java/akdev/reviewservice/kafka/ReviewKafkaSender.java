package com.java.akdev.reviewservice.kafka;

import com.java.akdev.commonmodels.dto.ReviewMessage;

public interface ReviewKafkaSender {

    void sendReview(ReviewMessage review);
}
