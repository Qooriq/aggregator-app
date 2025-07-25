package com.java.akdev.driverservice.integration;

import com.java.akdev.commonmodels.dto.UserResponse;
import com.java.akdev.driverservice.IntegrationTestBase;
import com.java.akdev.driverservice.artemis.ReviewListener;
import com.java.akdev.driverservice.dto.DriverCreateDto;
import com.java.akdev.driverservice.enumeration.Order;
import com.java.akdev.driverservice.enumeration.SortField;
import com.java.akdev.driverservice.util.TestSetUps;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.UUID;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


@ActiveProfiles("test")
public class DriverIntegrationTest extends IntegrationTestBase {

    @MockitoBean
    private ReviewListener reviewListener;

    private UUID id;
    private Integer page;
    private Integer size;
    private SortField sortField;
    private Order order;
    private DriverCreateDto driverCreateDto;
    private UserResponse driverReadDto;
    private UserResponse driverUpdateReadDto;

    @BeforeEach
    public void setUp() {
        id = TestSetUps.id;
        page = TestSetUps.DEFAULT_PAGE;
        size = TestSetUps.DEFAULT_PAGE_SIZE;
        sortField = TestSetUps.SORT_FIELD;
        order = TestSetUps.ORDER;
        driverCreateDto = TestSetUps.getCreateDto();
        driverReadDto = TestSetUps.getReadDto();
        driverUpdateReadDto = TestSetUps.getUpdateDto();
    }

    @Test
    void findAll() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/drivers")
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
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/drivers/{id}", id.toString()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.firstName").value(driverReadDto.firstName()))
                .andExpect(jsonPath("$.lastName").value(driverReadDto.lastName()))
                .andExpect(jsonPath("$.username").value(driverReadDto.username()));
    }

    @Test
    void findByIdNotFound() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/drivers/{id}", UUID.randomUUID().toString()))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(jsonPath("id").value("driver not found"));
    }

    @Test
    void createDriver() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/drivers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(driverCreateDto)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(jsonPath("$.firstName").value(driverUpdateReadDto.firstName()))
                .andExpect(jsonPath("$.lastName").value(driverUpdateReadDto.lastName()))
                .andExpect(jsonPath("$.username").value(driverUpdateReadDto.username()));
    }

    @Test
    void updateDriver() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/drivers/{id}", id.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(driverCreateDto)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.firstName").value(driverUpdateReadDto.firstName()))
                .andExpect(jsonPath("$.lastName").value(driverUpdateReadDto.lastName()))
                .andExpect(jsonPath("$.username").value(driverUpdateReadDto.username()));
    }

    @Test
    void deleteDriver() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/drivers/{id}", id.toString()))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

}
