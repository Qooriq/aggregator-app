package com.java.akdev.ridesservice.client;

import com.java.akdev.ridesservice.dto.UserResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@FeignClient(name = "driver-feign-client", url = "localhost:8084/api/v1/drivers")
public interface CheckDriverExistClient {

    @GetMapping("/{id}")
    ResponseEntity<UserResponse> findDriverById(@PathVariable("id") UUID id);
}
