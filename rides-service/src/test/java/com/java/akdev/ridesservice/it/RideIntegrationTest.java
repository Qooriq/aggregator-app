package com.java.akdev.ridesservice.it;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.java.akdev.ridesservice.IntegrationTestBase;
import com.java.akdev.ridesservice.dto.RideCreateDto;
import com.java.akdev.ridesservice.dto.RideReadDto;
import com.java.akdev.ridesservice.dto.RideUpdateDto;
import com.java.akdev.ridesservice.enumeration.Order;
import com.java.akdev.ridesservice.enumeration.SortField;
import com.java.akdev.ridesservice.util.TestSetUps;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@Rollback
@ActiveProfiles("test")
public class RideIntegrationTest extends IntegrationTestBase {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    private Integer page;
    private Integer size;
    private SortField sortField;
    private Order order;
    private Long id;
    private RideReadDto rideReadDto;
    private RideCreateDto rideCreateDto;
    private RideUpdateDto rideUpdateDto;
    private RideReadDto rideUpdateReadDto;

    @BeforeEach
    void setUp() {
        id = TestSetUps.id;
        page = TestSetUps.DEFAULT_PAGE;
        size = TestSetUps.DEFAULT_PAGE_SIZE;
        order = TestSetUps.ORDER;
        sortField = TestSetUps.SORT_FIELD;
        rideReadDto = TestSetUps.getReadDto();
        rideCreateDto = TestSetUps.getCreateDto();
        rideUpdateDto = TestSetUps.getRideUpdateDto();
        rideUpdateReadDto = TestSetUps.getUpdateDto();
    }

    @Test
    void findAll() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/rides")
                        .param("page", page.toString())
                        .param("size", size.toString())
                        .param("sortField", sortField.name())
                        .param("order", order.name()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.content.length()").isNotEmpty());
    }

    @Test
    void findById() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/rides/{id}", id.toString()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.startLocation").value(rideReadDto.startLocation()))
                .andExpect(jsonPath("$.endLocation").value(rideReadDto.endLocation()))
                .andExpect(jsonPath("$.ridePrice").value(rideReadDto.ridePrice()))
                .andExpect(jsonPath("$.driver").value(rideReadDto.driver()));
    }

    @Test
    void create() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/rides")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(rideCreateDto)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(jsonPath("$.startLocation").value(rideReadDto.startLocation()))
                .andExpect(jsonPath("$.endLocation").value(rideReadDto.endLocation()))
                .andExpect(jsonPath("$.ridePrice").value(rideReadDto.ridePrice()))
                .andExpect(jsonPath("$.driver").value(rideReadDto.driver()));
    }

    @Test
    void update() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/rides/{id}", id.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(rideUpdateDto)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.startLocation").value(rideUpdateDto.startLocation()))
                .andExpect(jsonPath("$.endLocation").value(rideUpdateDto.endLocation()))
                .andExpect(jsonPath("$.ridePrice").value(rideUpdateDto.ridePrice()))
                .andExpect(jsonPath("$.driver").value(rideUpdateReadDto.driver()));
    }

    @Test
    void delete() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/rides/{id}", id.toString()))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }
}
