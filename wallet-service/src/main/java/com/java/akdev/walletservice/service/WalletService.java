package com.java.akdev.walletservice.service;

import com.java.akdev.walletservice.dto.WalletResponse;
import com.java.akdev.walletservice.dto.WalletCreateDto;
import com.java.akdev.walletservice.dto.WalletReadDto;
import com.java.akdev.walletservice.enumeration.Order;
import com.java.akdev.walletservice.enumeration.SortField;
import org.springframework.data.domain.Page;

import java.util.UUID;

public interface WalletService {

    Page<WalletReadDto> findAll(Integer page, Integer size, SortField sortField, Order order);

    WalletReadDto findById(Long id);

    WalletReadDto update(Long id, WalletCreateDto dto);

    WalletReadDto createWallet(WalletCreateDto dto);

    void deleteWallet(Long id);

    WalletResponse payment(UUID userId, Double amount);
}
