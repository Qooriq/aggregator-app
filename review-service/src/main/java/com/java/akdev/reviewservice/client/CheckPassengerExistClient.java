package com.java.akdev.reviewservice.client;

import com.java.akdev.commonmodels.dto.UserResponse;
import com.java.akdev.reviewservice.config.FeignConfiguration;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@FeignClient(name = "${feign.passenger_name}", url = "${feign.url.passenger}", configuration = FeignConfiguration.class)
public interface CheckPassengerExistClient {

    @GetMapping("/{id}")
    ResponseEntity<UserResponse> findPassengerById(@PathVariable("id") UUID id);
}
