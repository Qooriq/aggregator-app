package com.java.akdev.ridesservice.client;

import com.java.akdev.commonmodels.dto.WalletResponse;
import com.java.akdev.ridesservice.config.FeignConfiguration;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

@FeignClient(name = "${feign.name.wallet}", configuration = FeignConfiguration.class)
public interface WalletFeignClient {

    @PutMapping("/api/v1/wallets/payment/{id}")
    @CircuitBreaker(name = "walletClient")
    @Retry(name = "walletClientRetry")
    ResponseEntity<WalletResponse> updateWallet(@PathVariable("id") UUID passengerId,
                                                @RequestParam Double price);
}
