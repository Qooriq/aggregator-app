package com.java.akdev.reviewservice.artemis;

import com.java.akdev.commonmodels.dto.ReviewMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReviewArtemisProducer {

    private final JmsTemplate jmsTemplate;

    public void sendReview(ReviewMessage review) {
        jmsTemplate.convertAndSend("review-drivers-update", review, message -> {
            message.setStringProperty("_type", "ReviewMessage");
            return message;
        });
    }
}
