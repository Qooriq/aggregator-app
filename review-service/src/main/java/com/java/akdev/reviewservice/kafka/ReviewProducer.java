package com.java.akdev.reviewservice.kafka;

import com.java.akdev.reviewservice.dto.ReviewMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReviewProducer implements ReviewSender {

    @Value("${review-service.kafka.topic-name-passenger}")
    private String topicPassengerName;
    @Value("${review-service.kafka.topic-name-driver}")
    private String topicDriverName;
    private final KafkaTemplate<Long, Object> kafkaTemplate;


    @Override
    public void sendPassengerReview(ReviewMessage review) {
        kafkaTemplate.send(topicPassengerName, review);
    }

    @Override
    public void sendDriverReview(ReviewMessage review) {
        kafkaTemplate.send(topicDriverName, review);
    }
}
