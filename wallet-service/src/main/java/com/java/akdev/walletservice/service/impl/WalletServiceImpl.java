package com.java.akdev.walletservice.service.impl;

import com.java.akdev.walletservice.client.CheckDriverFeignClient;
import com.java.akdev.walletservice.client.CheckPassengerFeignClient;
import com.java.akdev.walletservice.dto.WalletCreateDto;
import com.java.akdev.walletservice.dto.WalletReadDto;
import com.java.akdev.walletservice.dto.WalletResponse;
import com.java.akdev.walletservice.enumeration.OperationResult;
import com.java.akdev.walletservice.enumeration.Order;
import com.java.akdev.walletservice.enumeration.SortField;
import com.java.akdev.walletservice.enumeration.WalletOwner;
import com.java.akdev.walletservice.exception.UserNotFoundException;
import com.java.akdev.walletservice.exception.WalletNotFoundException;
import com.java.akdev.walletservice.mapper.WalletMapper;
import com.java.akdev.walletservice.repository.WalletRepository;
import com.java.akdev.walletservice.service.WalletService;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class WalletServiceImpl implements WalletService {

    private final WalletRepository walletRepository;
    private final WalletMapper walletMapper;
    private final CheckPassengerFeignClient passengerClient;
    private final CheckDriverFeignClient driverClient;
    private final static String ERROR_MESSAGE = "WalletController.wallet.notFound";
    private final static String USER_NOT_EXISTS = "WalletController.userNotFound.error";

    @Transactional(readOnly = true)
    public Page<WalletReadDto> findAll(Integer page, Integer size, SortField sortField, Order order) {
        Sort.Direction direction = getDirection(order);
        PageRequest pageRequest = PageRequest.of(page, size, direction, sortField.getName());
        return walletRepository.findAll(pageRequest)
                .map(walletMapper::toWalletReadDto);
    }

    public WalletReadDto findById(Long id) {
        return walletRepository
                .findById(id)
                .map(walletMapper::toWalletReadDto)
                .orElseThrow(() -> new WalletNotFoundException(ERROR_MESSAGE));
    }

    @Transactional
    public WalletReadDto update(Long id, WalletCreateDto dto) {
        try {
            if (dto.walletOwner().equals(WalletOwner.DRIVER)) {
                driverClient.findDriverById(dto.userId()).getBody();
            } else {
                passengerClient.findPassengerById(dto.userId()).getBody();
            }
            return walletRepository.findById(id)
                    .map(wallet -> {
                        walletMapper.map(wallet, dto);
                        walletRepository.save(wallet);
                        return walletMapper.toWalletReadDto(wallet);
                    })
                    .orElseThrow(() -> new WalletNotFoundException(ERROR_MESSAGE));
        } catch (FeignException e) {
            throw new UserNotFoundException(USER_NOT_EXISTS);
        }

    }

    public WalletReadDto createWallet(WalletCreateDto dto) {
        try {
            if (dto.walletOwner().equals(WalletOwner.DRIVER)) {
                driverClient.findDriverById(dto.userId());
            } else {
                passengerClient.findPassengerById(dto.userId());
            }
            var wallet = walletMapper.toWallet(dto);
            var res = walletRepository.save(wallet);
            return walletMapper.toWalletReadDto(res);
        } catch (FeignException e) {
            throw new UserNotFoundException(USER_NOT_EXISTS);
        }
    }

    @Transactional
    public void deleteWallet(Long id) {
        walletRepository.deleteById(id);
    }

    @Override
    public WalletResponse payment(UUID userId, Double amount) {
        var wallet = walletRepository.findByUserId(userId);
        var curAmount = wallet.getAmount();
        if (curAmount < amount) {
            return new WalletResponse(OperationResult.DECLINED);
        }
        wallet.setAmount(curAmount - amount);
        walletRepository.save(wallet);
        return new WalletResponse(OperationResult.SUCCESSFUL);
    }


    private Sort.Direction getDirection(Order order) {
        return order == Order.DESC ? Sort.Direction.DESC : Sort.Direction.ASC;
    }

}
