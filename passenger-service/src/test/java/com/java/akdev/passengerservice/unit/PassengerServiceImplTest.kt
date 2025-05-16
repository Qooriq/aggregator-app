package com.java.akdev.passengerservice.unit

import com.java.akdev.passengerservice.dto.PassengerCreateDto
import com.java.akdev.passengerservice.dto.PassengerReadDto
import com.java.akdev.passengerservice.entity.Passenger
import com.java.akdev.passengerservice.exception.PassengerNotFoundException
import com.java.akdev.passengerservice.mapper.PassengerMapper
import com.java.akdev.passengerservice.repository.PassengerRepository
import com.java.akdev.passengerservice.service.impl.PassengerServiceImpl
import com.java.akdev.passengerservice.util.TestSetUps
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.junit.jupiter.MockitoExtension
import java.util.*

@ExtendWith(MockitoExtension::class)
class PassengerServiceImplTest {

    @Mock
    private lateinit var passengerRepository: PassengerRepository

    @Mock
    private lateinit var passengerMapper: PassengerMapper

    @InjectMocks
    private lateinit var passengerService: PassengerServiceImpl

    private lateinit var passenger: Passenger
    private lateinit var id: UUID
    private lateinit var createDto: PassengerCreateDto
    private lateinit var readDto: PassengerReadDto

    @BeforeEach
    fun setUp() {
        passenger = TestSetUps.getPassenger()
        id = TestSetUps.id
        createDto = TestSetUps.getCreateDto()
        readDto = TestSetUps.getReadDto()
    }


    @Test
    fun `find passenger by id`() {

        `when`(
            passengerRepository.findById(id)
        ).thenReturn(Optional.of(passenger))
        `when`(
            passengerMapper.toReadDto(passenger)
        ).thenReturn(readDto)

        val pass: PassengerReadDto? = passengerService.findPassengerById(id)

        assertThat(pass).isEqualTo(readDto)

        verify(passengerRepository)!!.findById(id)
        verify(passengerMapper)!!.toReadDto(passenger)
    }

    @Test
    fun `update passenger by id`() {

        `when`(
            passengerRepository.findById(passenger.id!!)
        ).thenReturn(Optional.of(passenger))
        `when`(
            passengerRepository.save(passenger)
        ).thenReturn(passenger)
        `when`(
            passengerMapper.toReadDto(passenger)
        ).thenReturn(readDto)

        val pas: PassengerReadDto? = passengerService.updatePassenger(id, createDto)

        assertThat(pas)
            .isEqualTo(readDto)

        verify(passengerRepository)!!.findById(id)
        verify(passengerRepository)!!.save(passenger)
        verify(passengerMapper)!!.toReadDto(passenger)
    }

    @Test
    fun `create passenger`() {
        `when`(
            passengerRepository.save(passenger)
        ).thenReturn(passenger)
        `when`(
            passengerMapper.toReadDto(passenger)
        ).thenReturn(readDto)
        `when`(
            passengerMapper.toPassenger(createDto)
        ).thenReturn(passenger)

        val pas: PassengerReadDto? = passengerService.createPassenger(createDto)

        assertThat(pas)
            .isEqualTo(readDto)

        verify(passengerRepository)!!.save(passenger)
        verify(passengerMapper)!!.toReadDto(passenger)
        verify(passengerMapper)!!.toPassenger(createDto)
    }

    @Test
    fun `delete passenger by id`() {
        `when`(
            passengerRepository.findById(id)
        ).thenReturn(Optional.of(passenger))
        `when`(
            passengerRepository.save(passenger)
        ).thenReturn(passenger)

        assertThat(passengerService.deletePassenger(id))

        verify(passengerRepository)!!.findById(id)
        verify(passengerRepository)!!.save(passenger)
    }

    @Test
    fun `find not existing user`() {
        `when`(
            passengerRepository.findById(passenger.id!!)
        ).thenReturn(Optional.empty())

        assertThatThrownBy {
            passengerService.findPassengerById(id)
        }.isInstanceOf(PassengerNotFoundException::class.java)

        verify(passengerRepository)!!.findById(passenger.id!!)
    }
}