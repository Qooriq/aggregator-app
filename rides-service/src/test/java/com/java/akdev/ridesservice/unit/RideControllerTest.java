package com.java.akdev.ridesservice.unit;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.java.akdev.ridesservice.controller.RideController;
import com.java.akdev.ridesservice.dto.RideCreateDto;
import com.java.akdev.ridesservice.dto.RideReadDto;
import com.java.akdev.ridesservice.dto.RideUpdateDto;
import com.java.akdev.ridesservice.service.RideService;
import com.java.akdev.ridesservice.util.TestSetUps;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(RideController.class)
public class RideControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private RideService rideService;

    private Long id;
    private RideCreateDto rideCreateDto;
    private RideUpdateDto rideUpdateDto;
    private RideReadDto rideReadDto;

    @BeforeEach
    void setUp() {
        id = TestSetUps.id;
        rideCreateDto = TestSetUps.getCreateDto();
        rideReadDto = TestSetUps.getReadDto();
        rideUpdateDto = TestSetUps.getRideUpdateDto();
    }

    @Test
    void findRideById() throws Exception {
        when(rideService.findById(id))
                .thenReturn(rideReadDto);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/rides/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(rideReadDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.startLocation").value(rideReadDto.startLocation()))
                .andExpect(jsonPath("$.endLocation").value(rideReadDto.endLocation()))
                .andExpect(jsonPath("$.ridePrice").value(rideReadDto.ridePrice().toString()))
                .andExpect(jsonPath("$.driver").value(rideReadDto.driver()));
    }

    @Test
    void createRide() throws Exception {
        when(rideService.create(rideCreateDto))
                .thenReturn(rideReadDto);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/rides")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(rideCreateDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.startLocation").value(rideReadDto.startLocation()))
                .andExpect(jsonPath("$.endLocation").value(rideReadDto.endLocation()))
                .andExpect(jsonPath("$.ridePrice").value(rideReadDto.ridePrice().toString()))
                .andExpect(jsonPath("$.driver").value(rideReadDto.driver()));
    }

    @Test
    void updateRide() throws Exception {
        when(rideService.update(id, rideUpdateDto))
                .thenReturn(rideReadDto);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/rides/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(rideUpdateDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.startLocation").value(rideReadDto.startLocation()))
                .andExpect(jsonPath("$.endLocation").value(rideReadDto.endLocation()))
                .andExpect(jsonPath("$.ridePrice").value(rideReadDto.ridePrice().toString()))
                .andExpect(jsonPath("$.driver").value(rideReadDto.driver()));
    }

    @Test
    void deleteRide() throws Exception {
        doNothing().when(rideService).delete(id);

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/rides/{id}", id))
                .andExpect(status().isNoContent());
    }
}
