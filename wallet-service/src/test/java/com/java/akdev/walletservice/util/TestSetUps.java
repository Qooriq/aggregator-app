package com.java.akdev.walletservice.util;

import com.java.akdev.walletservice.dto.WalletCreateDto;
import com.java.akdev.walletservice.dto.WalletReadDto;
import com.java.akdev.walletservice.entity.Wallet;
import lombok.experimental.UtilityClass;

import java.util.UUID;

@UtilityClass
public class TestSetUps {

    public  final static Long ID = 1L;
    public  final static String DEFAULT_PAGE = "1";
    public  final static String DEFAULT_SIZE = "10";
    private final static Double AMOUNT  = 120.0;
    private final static Double NEW_AMOUNT  = 121.0;
    private final static UUID PASSENGER_ID = UUID.randomUUID();
    private final static String CARD_NUMBER = "12345678";

    public static Wallet getWallet() {
        return Wallet.builder()
                .id(ID)
                .amount(AMOUNT)
                .cardNumber(CARD_NUMBER)
                .userId(PASSENGER_ID)
                .build();
    }

    public static WalletCreateDto getCreateDto() {
        return new WalletCreateDto(
                CARD_NUMBER, AMOUNT, PASSENGER_ID
        );
    }

    public static Wallet getUpdateWallet() {
        return Wallet.builder()
                .id(ID)
                .amount(NEW_AMOUNT)
                .cardNumber(CARD_NUMBER)
                .userId(PASSENGER_ID)
                .build();
    }

    public static WalletReadDto getUpdateDto() {
        return new WalletReadDto(CARD_NUMBER, NEW_AMOUNT);
    }

    public static WalletReadDto getReadDto() {
        return new WalletReadDto(CARD_NUMBER, AMOUNT);
    }

    public static WalletCreateDto getUpdateCreateDto() {
        return new WalletCreateDto(CARD_NUMBER, NEW_AMOUNT, PASSENGER_ID);
    }
}
