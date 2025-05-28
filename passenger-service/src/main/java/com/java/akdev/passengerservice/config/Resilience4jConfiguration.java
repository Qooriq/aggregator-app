package com.java.akdev.passengerservice.config;

import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
public class Resilience4jConfiguration {

    @Value("${passenger-service.resilience4j.failure-rate}")
    private int failureRateThreshold;
    @Value("${passenger-service.resilience4j.duration-in-open-state}")
    private int durationInOpenState;
    @Value("${passenger-service.resilience4j.sliding-window-size}")
    private int slidingWindowSize;

    @Bean
    public CircuitBreakerRegistry circuitBreakerRegistry() {
        return CircuitBreakerRegistry.ofDefaults();
    }

    @Bean
    public CircuitBreakerConfig customCircuitBreakerConfig() {
        return CircuitBreakerConfig.custom()
                .failureRateThreshold(failureRateThreshold)
                .waitDurationInOpenState(Duration.ofMillis(durationInOpenState))
                .slidingWindowSize(slidingWindowSize)
                .build();
    }
}
