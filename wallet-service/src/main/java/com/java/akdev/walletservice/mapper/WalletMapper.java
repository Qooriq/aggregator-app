package com.java.akdev.walletservice.mapper;


import com.java.akdev.walletservice.config.MapperConfiguration;
import com.java.akdev.walletservice.dto.WalletCreateDto;
import com.java.akdev.walletservice.dto.WalletReadDto;
import com.java.akdev.walletservice.entity.Wallet;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(config = MapperConfiguration.class)
public interface WalletMapper {

    WalletReadDto toWalletReadDto(Wallet wallet);

    Wallet toWallet(WalletCreateDto dto);

    void map(@MappingTarget Wallet wallet, WalletCreateDto dto);
}
