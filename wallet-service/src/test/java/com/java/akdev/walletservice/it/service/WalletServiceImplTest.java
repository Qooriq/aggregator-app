package com.java.akdev.walletservice.it.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.java.akdev.walletservice.IntegrationTestBase;
import com.java.akdev.walletservice.enumeration.Order;
import com.java.akdev.walletservice.enumeration.SortField;
import com.java.akdev.walletservice.util.TestSetUps;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class WalletServiceImplTest extends IntegrationTestBase {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper = new ObjectMapper();

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
                .andExpect(MockMvcResultMatchers.jsonPath("$.content").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.length()").isNotEmpty());
    }

}
