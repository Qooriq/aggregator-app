package com.java.akdev.reviewservice.client;

import com.java.akdev.commonmodels.dto.UserResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@FeignClient(name = "passenger-feign-client", url = "localhost:8082/api/v1/passengers")
public interface CheckPassengerExistClient {

    @GetMapping("/{id}")
    ResponseEntity<UserResponse> findPassengerById(@PathVariable("id") UUID id);
}
