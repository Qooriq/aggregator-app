package com.java.akdev.walletservice.client;

import com.java.akdev.walletservice.dto.UserReadDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@FeignClient(name = "driver-feign-client", url = "localhost:8083/api/v1/drivers")
public interface CheckDriverFeignClient {

    @GetMapping("/{id}")
    ResponseEntity<UserReadDto> findDriverById(@PathVariable("id") UUID id);
}
