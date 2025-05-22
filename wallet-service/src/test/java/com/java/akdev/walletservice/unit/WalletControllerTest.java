package com.java.akdev.walletservice.unit;

import com.java.akdev.walletservice.controller.WalletController;
import com.java.akdev.walletservice.dto.WalletCreateDto;
import com.java.akdev.walletservice.dto.WalletReadDto;
import com.java.akdev.walletservice.service.impl.WalletServiceImpl;
import com.java.akdev.walletservice.util.TestSetUps;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class WalletControllerTest {

    @Mock
    private WalletServiceImpl walletService;
    @InjectMocks
    private WalletController walletController;

    private Long id;
    private WalletReadDto walletReadDto;
    private WalletCreateDto walletCreateDto;

    @BeforeEach
    void setUp() {
        id = TestSetUps.ID;
        walletReadDto = TestSetUps.getReadDto();
        walletCreateDto = TestSetUps.getCreateDto();
    }


    @Test
    void getWalletById() {
        when(walletService.findById(id))
                .thenReturn(walletReadDto);

        var res = walletController.findById(id);

        assertThat(res.getBody())
                .isEqualTo(walletReadDto);

        verify(walletService).findById(id);
    }

    @Test
    void createWallet() {
        when(walletService.createWallet(walletCreateDto))
                .thenReturn(walletReadDto);

        var res = walletController.create(walletCreateDto);

        assertThat(res.getBody())
                .isEqualTo(walletReadDto);

        verify(walletService).createWallet(walletCreateDto);
    }

    @Test
    void updateWallet() {
        when(walletService.update(id, walletCreateDto))
                .thenReturn(walletReadDto);

        var res = walletController.update(id, walletCreateDto);

        assertThat(res.getBody())
                .isEqualTo(walletReadDto);

        verify(walletService).update(id, walletCreateDto);
    }

    @Test
    void deleteWallet() {
        doNothing().when(walletService).deleteWallet(id);

        walletController.delete(id);

        verify(walletService).deleteWallet(id);
    }
}
