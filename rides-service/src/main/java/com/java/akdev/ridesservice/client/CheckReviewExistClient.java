package com.java.akdev.ridesservice.client;

import com.java.akdev.commonmodels.dto.ReviewResponse;
import com.java.akdev.ridesservice.config.FeignConfiguration;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "${feign.review_name}", url = "${feign.url.review}", configuration = FeignConfiguration.class)
public interface CheckReviewExistClient {

    @GetMapping("/{id}")
    @CircuitBreaker(name = "reviewClient")
    @Retry(name = "reviewClientRetry")
    ResponseEntity<ReviewResponse> findReviewById(@PathVariable("id") Long id);
}
