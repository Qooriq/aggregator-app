package com.java.akdev.reviewservice.client;

import com.java.akdev.commonmodels.dto.UserResponse;
import com.java.akdev.reviewservice.config.FeignConfiguration;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@FeignClient(name = "${feign.name.passenger}", configuration = FeignConfiguration.class)
public interface CheckPassengerExistClient {

    @GetMapping("/api/v1/passengers/{id}")
    @CircuitBreaker(name = "passengerClient")
    @Retry(name = "passengerClientRetry")
    ResponseEntity<UserResponse> findPassengerById(@PathVariable("id") UUID id);
}
