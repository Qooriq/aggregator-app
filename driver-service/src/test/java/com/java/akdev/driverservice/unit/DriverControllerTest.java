package com.java.akdev.driverservice.unit;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.java.akdev.driverservice.controller.DriverController;
import com.java.akdev.driverservice.dto.DriverCreateDto;
import com.java.akdev.driverservice.dto.DriverReadDto;
import com.java.akdev.driverservice.service.DriverService;
import com.java.akdev.driverservice.util.TestSetUps;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.UUID;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@WebMvcTest(DriverController.class)
public class DriverControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private DriverService driverService;

    private UUID id;
    private DriverCreateDto driverCreateDto;
    private DriverReadDto driverReadDto;

    @BeforeEach
    void setUp() {
        id = TestSetUps.id;
        driverCreateDto = TestSetUps.getCreateDto();
        driverReadDto = TestSetUps.getReadDto();
    }

    @Test
    void getDriver() throws Exception {
        when(driverService.findDriverById(id))
                .thenReturn(driverReadDto);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/drivers/{id}", id.toString()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value(driverReadDto.firstName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value(driverReadDto.lastName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.username").value(driverReadDto.username()));
    }

    @Test
    void createDriver() throws Exception {
        when(driverService.createDriver(driverCreateDto))
                .thenReturn(driverReadDto);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/drivers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(driverCreateDto)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value(driverReadDto.firstName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value(driverReadDto.lastName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.username").value(driverReadDto.username()));
    }

    @Test
    void updateDriver() throws Exception {
        when(driverService.update(id, driverCreateDto))
                .thenReturn(driverReadDto);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/drivers/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(driverCreateDto)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value(driverReadDto.firstName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value(driverReadDto.lastName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.username").value(driverReadDto.username()));
    }

    @Test
    void deleteDriver() throws Exception {
        doNothing().when(driverService).deleteDriver(id);

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/drivers/{id}", id.toString()))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

}
