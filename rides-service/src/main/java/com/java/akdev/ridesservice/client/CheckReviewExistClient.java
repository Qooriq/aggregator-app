package com.java.akdev.ridesservice.client;

import com.java.akdev.commonmodels.dto.ReviewResponse;
import com.java.akdev.ridesservice.config.FeignConfiguration;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "${feign.review_name}", url = "${feign.url.review}", configuration = FeignConfiguration.class)
public interface CheckReviewExistClient {

    @GetMapping("/{id}")
    ResponseEntity<ReviewResponse> findReviewById(@PathVariable("id") Long id);
}
