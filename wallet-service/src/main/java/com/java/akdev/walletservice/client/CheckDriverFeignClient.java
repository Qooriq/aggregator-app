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

@FeignClient(name = "${feign.driver_name}", url = "${feign.url.driver}", configuration = FeignConfiguration.class)
public interface CheckDriverFeignClient {

    @GetMapping("/{id}")
    @CircuitBreaker(name = "driverClient")
    @Retry(name = "driverClientRetry")
    ResponseEntity<UserReadDto> findDriverById(@PathVariable("id") UUID id);
}
