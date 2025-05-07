package com.java.akdev.walletservice.mapper;


import com.java.akdev.walletservice.configuration.MapperConfiguration;
import com.java.akdev.walletservice.dto.WalletCreateDto;
import com.java.akdev.walletservice.dto.WalletReadDto;
import com.java.akdev.walletservice.entity.Wallet;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(config = MapperConfiguration.class)
public interface WalletMapper {

    WalletReadDto toWalletReadDto(Wallet wallet);

    Wallet toWallet(WalletCreateDto dto);

    Wallet map(@MappingTarget Wallet wallet, WalletCreateDto dto);
}
