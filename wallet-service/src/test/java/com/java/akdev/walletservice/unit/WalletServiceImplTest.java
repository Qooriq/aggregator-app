package com.java.akdev.walletservice.unit;

import com.java.akdev.walletservice.dto.WalletCreateDto;
import com.java.akdev.walletservice.dto.WalletReadDto;
import com.java.akdev.walletservice.entity.Wallet;
import com.java.akdev.walletservice.exception.WalletNotFoundException;
import com.java.akdev.walletservice.mapper.WalletMapper;
import com.java.akdev.walletservice.repository.WalletRepository;
import com.java.akdev.walletservice.service.impl.WalletServiceImpl;
import com.java.akdev.walletservice.util.TestSetUps;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class WalletServiceImplTest {

    @Mock
    private WalletMapper walletMapper;
    @Mock
    private WalletRepository walletRepository;
    @InjectMocks
    private WalletServiceImpl walletService;

    private Wallet wallet;
    private Wallet updateWallet;
    private WalletCreateDto walletCreateDto;
    private WalletCreateDto walletUpdateCreateDto;
    private WalletReadDto walletUpdateDto;
    private WalletReadDto walletReadDto;
    private Long id;

    @BeforeEach
    void setUp() {
        wallet = TestSetUps.getWallet();
        id = TestSetUps.id;
        walletCreateDto = TestSetUps.getCreateDto();
        walletReadDto = TestSetUps.getReadDto();
        walletUpdateDto = TestSetUps.getUpdateDto();
        updateWallet = TestSetUps.getUpdateWallet();
        walletUpdateCreateDto = TestSetUps.getUpdateCreateDto();
    }

    @Test
    @DisplayName("find wallet by id")
    void findReviewById() {

        when(walletRepository.findById(id))
                .thenReturn(Optional.of(wallet));
        when(walletMapper.toWalletReadDto(wallet))
                .thenReturn(walletReadDto);

        var rev = walletService.findById(id);

        assertThat(rev)
                .isEqualTo(walletReadDto);

        verify(walletMapper).toWalletReadDto(wallet);
        verify(walletRepository).findById(id);
    }

    @Test
    @DisplayName("wallet not found exception")
    void findReviewNotFoundById() {

        when(walletRepository.findById(id))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() ->
                walletService.findById(id)
        ).isInstanceOf(WalletNotFoundException.class);

        verify(walletRepository).findById(id);
    }

    @Test
    @DisplayName("create wallet")
    void createReview() {
        when(walletRepository.save(wallet))
                .thenReturn(wallet);
        when(walletMapper.toWallet(walletCreateDto))
                .thenReturn(wallet);
        when(walletMapper.toWalletReadDto(wallet))
                .thenReturn(walletReadDto);

        var rev = walletService.createWallet(walletCreateDto);

        assertThat(rev)
                .isEqualTo(walletReadDto);

        verify(walletRepository).save(wallet);
        verify(walletMapper).toWallet(walletCreateDto);
        verify(walletMapper).toWalletReadDto(wallet);
    }

    @Test
    @DisplayName("update wallet by id")
    void update() {
        when(walletRepository.findById(id))
                .thenReturn(Optional.of(updateWallet));
        when(walletRepository.save(updateWallet))
                .thenReturn(updateWallet);
        when(walletMapper.toWalletReadDto(updateWallet))
                .thenReturn(walletUpdateDto);
        doNothing().when(walletMapper).map(updateWallet, walletUpdateCreateDto);

        var revDto = walletService.update(id, walletUpdateCreateDto);

        assertThat(revDto)
                .isEqualTo(walletUpdateDto);

        verify(walletRepository).findById(id);
        verify(walletRepository).save(updateWallet);
        verify(walletMapper).toWalletReadDto(updateWallet);
        verify(walletMapper).map(updateWallet, walletUpdateCreateDto);
    }

    @Test
    @DisplayName("delete wallet by id")
    void deleteReview() {
        doNothing().when(walletRepository).deleteById(id);

        walletService.deleteWallet(id);

        verify(walletRepository).deleteById(id);
    }
}