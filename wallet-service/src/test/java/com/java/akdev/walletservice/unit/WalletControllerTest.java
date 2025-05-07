package com.java.akdev.walletservice.unit;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.java.akdev.walletservice.controller.WalletController;
import com.java.akdev.walletservice.dto.WalletCreateDto;
import com.java.akdev.walletservice.dto.WalletReadDto;
import com.java.akdev.walletservice.service.impl.WalletServiceImpl;
import com.java.akdev.walletservice.util.TestSetUps;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@WebMvcTest(WalletController.class)
public class WalletControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private WalletServiceImpl walletService;

    private Long id;
    private WalletReadDto walletReadDto;
    private WalletCreateDto walletCreateDto;

    @BeforeEach
    void setUp() {
        id = TestSetUps.id;
        walletReadDto = TestSetUps.getReadDto();
        walletCreateDto = TestSetUps.getCreateDto();
    }


    @Test
    void getWalletById() throws Exception {
        when(walletService.findById(id))
                .thenReturn(walletReadDto);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/wallets/{id}", id))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.cardNumber").value(walletReadDto.cardNumber()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.amount").value(walletReadDto.amount()));
    }

    @Test
    void createWallet() throws Exception {
        when(walletService.createWallet(walletCreateDto))
                .thenReturn(walletReadDto);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/wallets")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(walletCreateDto))
                )
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.cardNumber").value(walletReadDto.cardNumber()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.amount").value(walletReadDto.amount()));
    }

    @Test
    void updateWallet() throws Exception {
        when(walletService.update(id, walletCreateDto))
                .thenReturn(walletReadDto);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/wallets/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(walletCreateDto)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.cardNumber").value(walletReadDto.cardNumber()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.amount").value(walletReadDto.amount()));
    }

    @Test
    void deleteWallet() throws Exception {
        doNothing().when(walletService).deleteWallet(id);

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/wallets/{id}", id))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }
}
