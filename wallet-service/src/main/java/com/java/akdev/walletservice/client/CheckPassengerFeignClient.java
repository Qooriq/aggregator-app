package com.java.akdev.walletservice.client;

import com.java.akdev.walletservice.dto.UserReadDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@FeignClient(name = "passenger-feign-client", url = "localhost:8082/api/v1/passengers")
public interface CheckPassengerFeignClient {

    @GetMapping("/{id}")
    ResponseEntity<UserReadDto> findPassengerById(@PathVariable UUID id);
}
