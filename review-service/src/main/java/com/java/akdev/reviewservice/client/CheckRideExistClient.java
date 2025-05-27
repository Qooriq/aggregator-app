package com.java.akdev.reviewservice.client;

import com.java.akdev.commonmodels.dto.RideResponse;
import com.java.akdev.reviewservice.config.FeignConfiguration;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "${feign.ride_name}", url = "${feign.url.ride}", configuration = FeignConfiguration.class)
public interface CheckRideExistClient {

    @GetMapping("/{id}")
    @CircuitBreaker(name = "rideClient")
    @Retry(name = "rideClientRetry")
    ResponseEntity<RideResponse> findRideById(@PathVariable("id") Long id);
}
