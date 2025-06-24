package com.java.akdev.walletservice.controller;

import com.java.akdev.commonmodels.dto.WalletResponse;
import com.java.akdev.walletservice.dto.WalletCreateDto;
import com.java.akdev.walletservice.dto.WalletReadDto;
import com.java.akdev.walletservice.enumeration.Order;
import com.java.akdev.walletservice.enumeration.SortField;
import com.java.akdev.walletservice.service.WalletService;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("api/v1/wallets")
@RequiredArgsConstructor
@Slf4j
public class WalletController {

    private final WalletService walletService;

    @GetMapping
    public ResponseEntity<Page<WalletReadDto>> findAll(@RequestParam @Min(1) Integer page,
                                                       @RequestParam @Min(1) Integer size,
                                                       @RequestParam SortField sortField,
                                                       @RequestParam Order order) {
        return ResponseEntity.status(200)
                .body(walletService.findAll(page, size, sortField, order));
    }

    @GetMapping("/{id}")
    public ResponseEntity<WalletReadDto> findById(@PathVariable @Min(1) Long id) {
        return ResponseEntity.status(200)
                .body(walletService.findById(id));
    }

    @PostMapping
    public ResponseEntity<WalletReadDto> create(@RequestBody WalletCreateDto walletCreateDto) {
        log.info("Create wallet with passenger id: {}", walletCreateDto.userId());
        return ResponseEntity.status(201)
                .body(walletService.createWallet(walletCreateDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<WalletReadDto> update(@PathVariable @Min(1) Long id,
                                                @RequestBody WalletCreateDto walletCreateDto) {
        return ResponseEntity.status(200)
                .body(walletService.update(id, walletCreateDto));
    }

    @PutMapping("/payment/{id}")
    ResponseEntity<WalletResponse> updateWallet(@PathVariable("id") UUID userId,
                                                @RequestParam Double price) {
        return ResponseEntity.status(200)
                .body(walletService.payment(userId, price));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable @Min(1) Long id) {
        walletService.deleteWallet(id);
        return ResponseEntity.noContent().build();
    }
}
