package com.java.akdev.walletservice.controller;

import com.java.akdev.walletservice.dto.WalletCreateDto;
import com.java.akdev.walletservice.dto.WalletReadDto;
import com.java.akdev.walletservice.enumeration.SortField;
import com.java.akdev.walletservice.service.WalletService;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/wallets")
@RequiredArgsConstructor
public class WalletController {

    private final WalletService walletService;

    @GetMapping
    public ResponseEntity<Page<WalletReadDto>> findAll(@RequestParam @Min(1) Integer page,
                                                       @RequestParam @Min(1) Integer size,
                                                       @RequestParam SortField sortField,
                                                       @RequestParam Sort.Direction order) {
        return ResponseEntity.status(200)
                .body(walletService.findAll(page - 1, size, sortField, order));
    }

    @GetMapping("/{id}")
    public ResponseEntity<WalletReadDto> findById(@PathVariable @Min(1) Long id) {
        return ResponseEntity.status(200)
                .body(walletService.findById(id));
    }

    @PostMapping
    public ResponseEntity<WalletReadDto> create(@RequestBody WalletCreateDto walletCreateDto) {
        return ResponseEntity.status(201)
                .body(walletService.createWallet(walletCreateDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<WalletReadDto> update(@PathVariable @Min(1) Long id,
                                                @RequestBody WalletCreateDto walletCreateDto) {
        return ResponseEntity.status(200)
                .body(walletService.update(id, walletCreateDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable @Min(1) Long id) {
        walletService.deleteWallet(id);
        return ResponseEntity.noContent().build();
    }
}
