package com.java.akdev.reviewservice.kafka;

import com.java.akdev.reviewservice.config.AppConfiguration;
import com.java.akdev.reviewservice.dto.ReviewMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReviewKafkaProducer implements ReviewKafkaSender {

    private final AppConfiguration appConfiguration;
    private final KafkaTemplate<Long, Object> kafkaTemplate;


    @Override
    public void sendReview(ReviewMessage review) {
        kafkaTemplate.send(
                appConfiguration
                        .kafkaConfigInfo()
                        .topicNamePassenger(),
                review
        );
    }

}