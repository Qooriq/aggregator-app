package com.java.akdev.reviewservice.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app")
public record AppConfiguration(
        KafkaConfigInfo kafkaConfigInfo,
        Integer page,
        Integer size
) {
    public record KafkaConfigInfo(
            String topicNamePassenger,
            Integer replicas,
            Integer partitions
    ) {
    }
}
