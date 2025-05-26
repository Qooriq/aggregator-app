package com.java.akdev.walletservice.client;

import com.java.akdev.walletservice.config.FeignConfiguration;
import com.java.akdev.walletservice.dto.UserReadDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@FeignClient(name = "${feign.driver_name}", url = "${feign.url.driver}", configuration = FeignConfiguration.class)
public interface CheckDriverFeignClient {

    @GetMapping("/{id}")
    ResponseEntity<UserReadDto> findDriverById(@PathVariable("id") UUID id);
}
