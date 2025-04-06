package com.java.akdev.walletservice.controller;

import com.java.akdev.walletservice.dto.WalletCreateDto;
import com.java.akdev.walletservice.dto.WalletReadDto;
import com.java.akdev.walletservice.service.WalletService;
import com.java.akdev.walletservice.util.SortType;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/wallets")
@RequiredArgsConstructor
public class WalletController {

    private final WalletService walletService;

    @GetMapping
    public ResponseEntity<Page<WalletReadDto>> findAll(@RequestParam Integer page,
                                                       @RequestParam Integer size,
                                                       @RequestBody SortType sortType) {
        return ResponseEntity.status(200)
                .body(walletService.findAll(page, size, sortType));
    }

    @GetMapping("/{id}")
    public ResponseEntity<WalletReadDto> findById(@PathVariable Long id) {
        return ResponseEntity.status(200)
                .body(walletService.findById(id));
    }

    @PostMapping
    public ResponseEntity<WalletReadDto> create(@RequestBody WalletCreateDto walletCreateDto) {
        return ResponseEntity.status(201)
                .body(walletService.createWallet(walletCreateDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<WalletReadDto> update(@PathVariable Long id,
                                                @RequestBody WalletCreateDto walletCreateDto) {
        return ResponseEntity.status(200)
                .body(walletService.update(id, walletCreateDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        walletService.deleteWallet(id);
        return ResponseEntity.noContent().build();
    }
}
