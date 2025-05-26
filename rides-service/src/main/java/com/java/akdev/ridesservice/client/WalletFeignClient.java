package com.java.akdev.ridesservice.client;

import com.java.akdev.commonmodels.dto.WalletResponse;
import com.java.akdev.ridesservice.config.FeignConfiguration;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

import java.util.UUID;

@FeignClient(name = "${feign.wallet_name}", url = "${feign.url.wallet}", configuration = FeignConfiguration.class)
public interface WalletFeignClient {

    @PutMapping("/payment/{id}")
    ResponseEntity<WalletResponse> updateWallet(@PathVariable("id") UUID passengerId, Double price);
}
