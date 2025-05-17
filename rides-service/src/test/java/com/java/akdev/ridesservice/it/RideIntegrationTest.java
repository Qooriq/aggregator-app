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
import org.junit.jupiter.api.DisplayName;
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

    private String startEndpoint;
    private String startEndpointWithId;
    private Integer page;
    private Integer size;
    private SortField sortField;
    private Order order;
    private Long id;
    private Long negativeId;
    private RideReadDto rideReadDto;
    private RideCreateDto rideCreateDto;
    private RideUpdateDto rideUpdateDto;
    private RideReadDto rideUpdateReadDto;

    @BeforeEach
    void setUp() {
        id = TestSetUps.id;
        negativeId = TestSetUps.negativeId;
        startEndpoint = TestSetUps.startEndpoint;
        startEndpointWithId = TestSetUps.startEndpointWithId;
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
    @DisplayName("find all rides")
    void givenPageSizeOrderSortField_findAll_returnArrayOfElements() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(startEndpoint)
                        .param("page", page.toString())
                        .param("size", size.toString())
                        .param("sortField", sortField.name())
                        .param("order", order.name()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.content.length()").isNotEmpty());
    }

    @Test
    @DisplayName("find ride by id")
    void givenRideId_findById_returnRide() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(startEndpointWithId, id.toString()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.startLocation").value(rideReadDto.startLocation()))
                .andExpect(jsonPath("$.endLocation").value(rideReadDto.endLocation()))
                .andExpect(jsonPath("$.ridePrice").value(rideReadDto.ridePrice()))
                .andExpect(jsonPath("$.driver").value(rideReadDto.driver()));
    }

    @Test
    @DisplayName("create ride with payload")
    void givenRidePayload_create_returnCreatedUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post(startEndpoint)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(rideCreateDto)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(jsonPath("$.startLocation").value(rideReadDto.startLocation()))
                .andExpect(jsonPath("$.endLocation").value(rideReadDto.endLocation()))
                .andExpect(jsonPath("$.ridePrice").value(rideReadDto.ridePrice()))
                .andExpect(jsonPath("$.driver").value(rideReadDto.driver()));
    }

    @Test
    @DisplayName("update ride by id")
    void givenRideIdRideWithUpdatePayload_update_returnUpdatedRide() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put(startEndpointWithId, id.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(rideUpdateDto)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.startLocation").value(rideUpdateDto.startLocation()))
                .andExpect(jsonPath("$.endLocation").value(rideUpdateDto.endLocation()))
                .andExpect(jsonPath("$.ridePrice").value(rideUpdateDto.ridePrice()))
                .andExpect(jsonPath("$.driver").value(rideUpdateReadDto.driver()));
    }

    @Test
    @DisplayName("delete ride by id")
    void givenRideId_delete_returnNothing() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete(startEndpointWithId, id.toString()))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    @DisplayName("throw bad request")
    void givenRideId_findById_throwBadRequest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(startEndpointWithId, negativeId.toString()))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
}
