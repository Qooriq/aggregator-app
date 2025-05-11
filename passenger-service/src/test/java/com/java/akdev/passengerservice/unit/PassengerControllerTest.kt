package com.java.akdev.passengerservice.unit

import com.fasterxml.jackson.databind.ObjectMapper
import com.java.akdev.passengerservice.controller.PassengerController
import com.java.akdev.passengerservice.dto.PassengerCreateDto
import com.java.akdev.passengerservice.dto.PassengerReadDto
import com.java.akdev.passengerservice.service.PassengerService
import com.java.akdev.passengerservice.util.TestSetUps
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.doNothing
import org.mockito.Mockito.`when`
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.context.bean.override.mockito.MockitoBean
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import java.util.*

@WebMvcTest(PassengerController::class)
class PassengerControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @MockitoBean
    private lateinit var passengerService: PassengerService

    private lateinit var id: UUID
    private lateinit var passengerCreateDto: PassengerCreateDto
    private lateinit var passengerReadDto: PassengerReadDto
    private lateinit var passengerUpdateReadDto: PassengerReadDto

    @BeforeEach
    fun setup() {
        id = TestSetUps.id
        passengerCreateDto = TestSetUps.getCreateDto()
        passengerReadDto = TestSetUps.getReadDto()
        passengerUpdateReadDto = TestSetUps.getUpdateReadDto()
    }


    @Test
    fun `find passenger by id`() {
        `when`(passengerService.findPassengerById(id))
            .thenReturn(passengerReadDto)

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/passengers/$id"))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(jsonPath("$.firstName").value(passengerReadDto.firstName))
            .andExpect(jsonPath("$.lastName").value(passengerReadDto.lastName))
            .andExpect(jsonPath("$.username").value(passengerReadDto.username))
    }

    @Test
    fun `create passenger`() {
        `when`(passengerService.createPassenger(passengerCreateDto))
            .thenReturn(passengerUpdateReadDto)

        mockMvc.perform(
            MockMvcRequestBuilders.post("/api/v1/passengers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(passengerCreateDto))
        )
            .andExpect(MockMvcResultMatchers.status().isCreated)
            .andExpect(jsonPath("$.firstName").value(passengerUpdateReadDto.firstName))
            .andExpect(jsonPath("$.lastName").value(passengerUpdateReadDto.lastName))
            .andExpect(jsonPath("$.username").value(passengerUpdateReadDto.username))
    }

    @Test
    fun `update passenger`() {
        `when`(passengerService.updatePassenger(id, passengerCreateDto))
            .thenReturn(passengerUpdateReadDto)

        mockMvc.perform(
            MockMvcRequestBuilders.put("/api/v1/passengers/$id")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(passengerCreateDto))
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(jsonPath("$.firstName").value(passengerUpdateReadDto.firstName))
            .andExpect(jsonPath("$.lastName").value(passengerUpdateReadDto.lastName))
            .andExpect(jsonPath("$.username").value(passengerUpdateReadDto.username))
    }

    @Test
    fun `delete passenger`() {
        doNothing().`when`(passengerService).deletePassenger(id)

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/passengers/$id"))
            .andExpect(MockMvcResultMatchers.status().isNoContent)
    }
}