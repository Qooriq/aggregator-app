package com.java.akdev.ridesservice.client;

import com.java.akdev.ridesservice.dto.ReviewResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "review-feign-client")
public interface CheckReviewExistClient {

    @GetMapping("/{id}")
    ResponseEntity<ReviewResponse> findReviewById(@PathVariable("id") Long id);
}
