package com.java.akdev.passengerservice.util

import com.java.akdev.passengerservice.dto.PassengerCreateDto
import com.java.akdev.passengerservice.dto.PassengerReadDto
import com.java.akdev.passengerservice.entity.Passenger
import com.java.akdev.passengerservice.enumeration.Order
import com.java.akdev.passengerservice.enumeration.SortField
import lombok.experimental.UtilityClass
import java.util.*

@UtilityClass
class TestSetUps {

    companion object {
        val id: UUID = UUID.fromString("4ebba608-6315-447e-9bf5-4e20da6fb0b0")
        const val DEFAULT_PAGE_SIZE: Int = 10
        const val DEFAULT_PAGE: Int = 0
        val SORT_FIELD: SortField = SortField.ID
        val ORDER: Order = Order.ASC
        private const val NAME: String = "Anton"
        private const val LAST_NAME: String = "Kazlouski"
        private const val EMAIL: String = "pochta@gmail.com"
        private const val NEW_EMAIL: String = "aboba@gmail.com"
        private const val PHONE_NUMBER: String = "1234567890"
        private const val PASSWORD: String = "1234"

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

        fun getDuplicatedEmail(): PassengerCreateDto {
            return PassengerCreateDto(NAME, LAST_NAME, EMAIL, PASSWORD, PHONE_NUMBER)
        }

        fun getCreateDto(): PassengerCreateDto {
            return PassengerCreateDto(NAME, LAST_NAME, NEW_EMAIL, PASSWORD, PHONE_NUMBER)
        }

        fun getReadDto(): PassengerReadDto {
            return PassengerReadDto(NAME, LAST_NAME, EMAIL)
        }

        fun getUpdateCreateDto(): PassengerCreateDto {
            return PassengerCreateDto(NAME, LAST_NAME, NEW_EMAIL, PASSWORD, PHONE_NUMBER)
        }

        fun getUpdateReadDto(): PassengerReadDto {
            return PassengerReadDto(NAME, LAST_NAME, NEW_EMAIL)
        }
    }
}