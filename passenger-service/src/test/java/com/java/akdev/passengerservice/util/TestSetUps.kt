package com.java.akdev.passengerservice.util

import com.java.akdev.passengerservice.dto.PassengerCreateDto
import com.java.akdev.passengerservice.dto.PassengerReadDto
import com.java.akdev.passengerservice.entity.Passenger
import java.util.*

class TestSetUps {

    companion object {
        val id: UUID = UUID.randomUUID()
        const val NAME: String = "Anton"
        const val LAST_NAME: String = "Kazlouski"
        const val EMAIL: String = "anton@gmail.com"
        const val PHONE_NUMBER: String = "1234567890"
        const val PASSWORD: String = "123"

        fun getPassenger(): Passenger {
            return Passenger.builder()
                .id(id)
                .firstName(NAME)
                .lastName(LAST_NAME)
                .username(EMAIL)
                .phoneNumber(PHONE_NUMBER)
                .password(PASSWORD)
                .build()
        }

        fun getCreateDto(): PassengerCreateDto {
            return PassengerCreateDto(NAME, LAST_NAME, EMAIL, PASSWORD, PHONE_NUMBER)
        }

        fun getReadDto(): PassengerReadDto {
            return PassengerReadDto(NAME, LAST_NAME, EMAIL)
        }
    }
}