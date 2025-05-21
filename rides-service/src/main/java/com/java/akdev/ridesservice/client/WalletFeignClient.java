package com.java.akdev.ridesservice.client;

import com.java.akdev.commonmodels.dto.WalletResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

import java.util.UUID;

@FeignClient(name = "wallet-client", url = "localhost:8085/api/v1/wallets")
public interface WalletFeignClient {

    @PutMapping("/payment/{id}")
    ResponseEntity<WalletResponse> updateWallet(@PathVariable("id") Long id, Double price, UUID passengerId);
}
