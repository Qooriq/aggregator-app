package com.java.akdev.walletservice.client;

import com.java.akdev.walletservice.config.FeignConfiguration;
import com.java.akdev.walletservice.dto.UserReadDto;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@FeignClient(name = "${feign.name.passenger}", configuration = FeignConfiguration.class)
public interface CheckPassengerFeignClient {

    @GetMapping("/api/v1/passengers/{id}")
    @CircuitBreaker(name = "passengerClient")
    @Retry(name = "passengerClientRetry")
    ResponseEntity<UserReadDto> findPassengerById(@PathVariable UUID id);
}
