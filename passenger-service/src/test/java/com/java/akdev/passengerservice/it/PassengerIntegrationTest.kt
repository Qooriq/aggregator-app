package com.java.akdev.passengerservice.it

import com.fasterxml.jackson.databind.ObjectMapper
import com.java.akdev.commonmodels.dto.UserResponse
import com.java.akdev.passengerservice.IntegrationTestBase
import com.java.akdev.passengerservice.dto.PassengerCreateDto
import com.java.akdev.passengerservice.enumeration.Order
import com.java.akdev.passengerservice.enumeration.SortField
import com.java.akdev.passengerservice.util.TestSetUps
import jakarta.transaction.Transactional
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.annotation.Rollback
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import java.util.*

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@Rollback
@ActiveProfiles("test")
open class PassengerIntegrationTest : IntegrationTestBase() {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    private var id: UUID = TestSetUps.id
    private var page: Int = TestSetUps.DEFAULT_PAGE
    private var pageSize: Int = TestSetUps.DEFAULT_PAGE_SIZE
    private var sortField: SortField = TestSetUps.SORT_FIELD
    private var order: Order = TestSetUps.ORDER
    private var UserResponse: UserResponse = TestSetUps.getReadDto()
    private var passengerCreatedDto: PassengerCreateDto = TestSetUps.getCreateDto()
    private var passengerUpdateDto: PassengerCreateDto = TestSetUps.getUpdateCreateDto()
    private var passengerUpdateReadDto: UserResponse = TestSetUps.getUpdateReadDto()


    @Test
    fun `find all passengers`() {
        mockMvc.perform(
            MockMvcRequestBuilders.get("/api/v1/passengers")
                .param("page", page.toString())
                .param("size", pageSize.toString())
                .param("sortField", sortField.name)
                .param("order", order.name)
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(jsonPath("$.content").isArray)
            .andExpect(jsonPath("$.content.length()").isNotEmpty)
    }

    @Test
    fun `find passenger by id`() {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/passengers/{id}", id.toString()))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(jsonPath("$.firstName").value(UserResponse.firstName))
            .andExpect(jsonPath("$.lastName").value(UserResponse.lastName))
            .andExpect(jsonPath("$.username").value(UserResponse.username))
    }

    @Test
    fun `passenger not found exception`() {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/passengers/{id}", UUID.randomUUID()))
            .andExpect(MockMvcResultMatchers.status().isNotFound)
            .andExpect(jsonPath("$.id").value("passenger not found"))
    }

    @Test
    fun `create passenger`() {
        mockMvc.perform(
            MockMvcRequestBuilders.post("/api/v1/passengers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(passengerCreatedDto))
        )
            .andExpect(MockMvcResultMatchers.status().isCreated)
            .andExpect(jsonPath("$.firstName").value(passengerUpdateReadDto.firstName))
            .andExpect(jsonPath("$.lastName").value(passengerUpdateReadDto.lastName))
            .andExpect(jsonPath("$.username").value(passengerUpdateReadDto.username))
    }

    @Test
    fun `update passenger by id`() {
        mockMvc.perform(
            MockMvcRequestBuilders.put("/api/v1/passengers/{id}", id.toString())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(passengerUpdateDto))
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(jsonPath("$.firstName").value(passengerUpdateReadDto.firstName))
            .andExpect(jsonPath("$.lastName").value(passengerUpdateReadDto.lastName))
            .andExpect(jsonPath("$.username").value(passengerUpdateReadDto.username))
    }

    @Test
    fun `delete passenger by id`() {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/passengers/{id}", id.toString()))
            .andExpect(MockMvcResultMatchers.status().isNoContent)
    }
}