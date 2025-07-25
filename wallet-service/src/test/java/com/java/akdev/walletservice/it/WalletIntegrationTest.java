package com.java.akdev.walletservice.it;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.java.akdev.walletservice.IntegrationTestBase;
import com.java.akdev.walletservice.client.CheckDriverFeignClient;
import com.java.akdev.walletservice.client.CheckPassengerFeignClient;
import com.java.akdev.walletservice.dto.UserReadDto;
import com.java.akdev.walletservice.dto.WalletCreateDto;
import com.java.akdev.walletservice.dto.WalletReadDto;
import com.java.akdev.walletservice.enumeration.Order;
import com.java.akdev.walletservice.enumeration.SortField;
import com.java.akdev.walletservice.util.TestSetUps;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@ActiveProfiles("test")
public class WalletIntegrationTest extends IntegrationTestBase {

    @MockitoBean
    private CheckDriverFeignClient checkDriverFeignClient;
    @MockitoBean
    private CheckPassengerFeignClient checkPassengerFeignClient;
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper = new ObjectMapper();

    private Long id;
    private WalletCreateDto walletCreateDto;
    private WalletCreateDto walletUpdateDto;
    private WalletReadDto walletReadDto;
    private WalletReadDto walletUpdateReadDto;

    @BeforeEach
    void setUp() {
        id = TestSetUps.ID;
        walletCreateDto = TestSetUps.getCreateDto();
        walletReadDto = TestSetUps.getReadDto();
        walletUpdateDto = TestSetUps.getUpdateCreateDto();
        walletUpdateReadDto = TestSetUps.getUpdateDto();
    }

    @Test
    void findAllWallets() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/wallets")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("page", TestSetUps.DEFAULT_PAGE)
                        .param("size", TestSetUps.DEFAULT_SIZE)
                        .param("sortField", String.valueOf(SortField.ID))
                        .param("order", String.valueOf(Order.ASC))
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.content.length()").isNotEmpty());
    }

    @Test
    void findWalletById() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/wallets/{id}", id))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.cardNumber").value(walletReadDto.cardNumber()))
                .andExpect(jsonPath("$.amount").value(walletReadDto.amount()));
    }

    @Test
    void createWallet() throws Exception {
        when(checkDriverFeignClient.findDriverById(any()))
                .thenReturn(ResponseEntity.ok(new UserReadDto("a", "a", "a")));
        when(checkPassengerFeignClient.findPassengerById(any()))
                .thenReturn(ResponseEntity.ok(new UserReadDto("a", "a", "a")));
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/wallets")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(walletCreateDto)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.cardNumber").value(walletCreateDto.cardNumber()))
                .andExpect(jsonPath("$.amount").value(walletCreateDto.amount()));
    }

    @Test
    void updateWallet() throws Exception {
        when(checkDriverFeignClient.findDriverById(any()))
                .thenReturn(ResponseEntity.ok(new UserReadDto("a", "a", "a")));
        when(checkPassengerFeignClient.findPassengerById(any()))
                .thenReturn(ResponseEntity.ok(new UserReadDto("a", "a", "a")));
        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/wallets/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(walletUpdateDto)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.cardNumber").value(walletUpdateReadDto.cardNumber()))
                .andExpect(jsonPath("$.amount").value(walletUpdateReadDto.amount()));
    }

    @Test
    void deleteWallet() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/wallets/{id}", id))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }
}
