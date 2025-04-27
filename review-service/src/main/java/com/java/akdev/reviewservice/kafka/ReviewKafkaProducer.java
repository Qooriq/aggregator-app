package com.java.akdev.reviewservice.kafka;

import com.java.akdev.reviewservice.dto.ReviewMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReviewKafkaProducer implements ReviewKafkaSender {

    @Value("${review-service.kafka.topic-name-passenger}")
    private String topicPassengerName;
    private final KafkaTemplate<Long, Object> kafkaTemplate;


    @Override
    public void sendReview(ReviewMessage review) {
        kafkaTemplate.send(topicPassengerName, review);
    }

}