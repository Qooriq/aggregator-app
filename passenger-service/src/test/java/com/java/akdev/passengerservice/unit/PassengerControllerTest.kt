package com.java.akdev.passengerservice.unit

import com.java.akdev.commonmodels.dto.UserResponse
import com.java.akdev.passengerservice.controller.PassengerController
import com.java.akdev.passengerservice.dto.PassengerCreateDto
import com.java.akdev.passengerservice.service.PassengerService
import com.java.akdev.passengerservice.util.TestSetUps
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.jupiter.MockitoExtension
import java.util.*

@ExtendWith(MockitoExtension::class)
class PassengerControllerTest {

    @Mock
    private lateinit var passengerService: PassengerService

    @InjectMocks
    private lateinit var passengerController: PassengerController

    private lateinit var id: UUID
    private lateinit var passengerCreateDto: PassengerCreateDto
    private lateinit var passengerReadDto: UserResponse

    @BeforeEach
    fun setup() {
        id = TestSetUps.id
        passengerCreateDto = TestSetUps.getCreateDto()
        passengerReadDto = TestSetUps.getReadDto()
    }


    @Test
    fun `find passenger by id`() {
        `when`(passengerService.findPassengerById(id))
            .thenReturn(passengerReadDto)

        val passenger = passengerController.findById(id)

        assertThat(passenger.body).isEqualTo(passengerReadDto)

        verify(passengerService).findPassengerById(id)
    }

    @Test
    fun `create passenger`() {
        `when`(passengerService.createPassenger(passengerCreateDto))
            .thenReturn(passengerReadDto)

        val passenger = passengerController.create(passengerCreateDto)

        assertThat(passenger.body)
            .isEqualTo(passengerReadDto)

        verify(passengerService).createPassenger(passengerCreateDto)
    }

    @Test
    fun `update passenger`() {
        `when`(passengerService.updatePassenger(id, passengerCreateDto))
            .thenReturn(passengerReadDto)

        val passenger = passengerController.update(id, passengerCreateDto)

        assertThat(passenger.body)
            .isEqualTo(passengerReadDto)

        verify(passengerService).updatePassenger(id, passengerCreateDto)
    }

    @Test
    fun `delete passenger`() {
        doNothing().`when`(passengerService).deletePassenger(id)

        passengerController.delete(id)

        verify(passengerService).deletePassenger(id)
    }
}