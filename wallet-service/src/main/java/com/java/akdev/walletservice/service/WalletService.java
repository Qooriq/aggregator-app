package com.java.akdev.walletservice.service;

import com.java.akdev.walletservice.dto.WalletCreateDto;
import com.java.akdev.walletservice.dto.WalletReadDto;
import com.java.akdev.walletservice.exception.WalletNotFoundException;
import com.java.akdev.walletservice.mapper.WalletMapper;
import com.java.akdev.walletservice.repository.WalletRepository;
import com.java.akdev.walletservice.util.SortType;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WalletService {

    private final WalletRepository walletRepository;
    private final WalletMapper walletMapper;

    public Page<WalletReadDto> findAll(Integer page, Integer size, SortType sortType) {
        return walletRepository.findAll(
                PageRequest.of(page,
                        size,
                        sortType.getOrder(),
                        sortType.getSortField().getName())
        ).map(walletMapper::toWalletReadDto);
    }

    public WalletReadDto findById(Long id) {
        return walletRepository
                .findById(id)
                .map(walletMapper::toWalletReadDto)
                .orElseThrow(WalletNotFoundException::new);
    }

    public WalletReadDto update(Long id, WalletCreateDto dto) {
        return walletRepository.findById(id)
                .map(wallet -> {
                    walletMapper.map(wallet, dto);
                    walletRepository.save(wallet);
                    return walletMapper.toWalletReadDto(wallet);
                })
                .orElseThrow(WalletNotFoundException::new);
    }

    public WalletReadDto createWallet(WalletCreateDto dto) {
        return walletMapper.toWalletReadDto(
                walletRepository
                        .save(walletMapper.toWallet(dto))
        );
    }

    public void deleteWallet(Long id) {
        walletRepository.deleteById(id);
    }


}
