package com.java.akdev.walletservice.service;

import com.java.akdev.walletservice.dto.WalletCreateDto;
import com.java.akdev.walletservice.dto.WalletReadDto;
import com.java.akdev.walletservice.enumeration.Order;
import com.java.akdev.walletservice.enumeration.SortField;
import com.java.akdev.walletservice.exception.WalletNotFoundException;
import com.java.akdev.walletservice.mapper.WalletMapper;
import com.java.akdev.walletservice.repository.WalletRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class WalletService {

    private final WalletRepository walletRepository;
    private final WalletMapper walletMapper;
    private final static String ERROR_MESSAGE = "WalletController.walletNotFound.error";

    @Transactional(readOnly = true)
    public Page<WalletReadDto> findAll(Integer page, Integer size, SortField sortField, Order order) {
        Sort.Direction direction;
        if (order == Order.DESC) {
            direction = Sort.Direction.DESC;
        } else {
            direction = Sort.Direction.ASC;
        }
        return walletRepository.findAll(
                PageRequest.of(page - 1,
                        size,
                        direction,
                        sortField.getName())
        ).map(walletMapper::toWalletReadDto);
    }

    @Transactional(readOnly = true)
    public WalletReadDto findById(Long id) {
        return walletRepository
                .findById(id)
                .map(walletMapper::toWalletReadDto)
                .orElseThrow(() -> new WalletNotFoundException(ERROR_MESSAGE));
    }

    @Transactional
    public WalletReadDto update(Long id, WalletCreateDto dto) {
        return walletRepository.findById(id)
                .map(wallet -> {
                    walletMapper.map(wallet, dto);
                    walletRepository.save(wallet);
                    return walletMapper.toWalletReadDto(wallet);
                })
                .orElseThrow(() -> new WalletNotFoundException(ERROR_MESSAGE));
    }

    @Transactional
    public WalletReadDto createWallet(WalletCreateDto dto) {
        return walletMapper.toWalletReadDto(
                walletRepository
                        .save(walletMapper.toWallet(dto))
        );
    }

    @Transactional
    public void deleteWallet(Long id) {
        walletRepository.deleteById(id);
    }


}
