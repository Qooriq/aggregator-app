package com.java.akdev.driverservice.util;

import com.java.akdev.driverservice.dto.DriverCreateDto;
import com.java.akdev.driverservice.dto.DriverReadDto;
import com.java.akdev.driverservice.entity.Driver;
import lombok.experimental.UtilityClass;

import java.util.UUID;

@UtilityClass
public class TestSetUps {

    public static UUID id = UUID.randomUUID();
    private final static String NAME = "Anton";
    private final static String LAST_NAME = "Kazlouski";
    private final static String EMAIL = "anton@gmail.com";
    private final static String PHONE_NUMBER = "1234567890";
    private final static String NEW_PHONE_NUMBER = "1234567";
    private final static String PASSWORD = "123";

    public static Driver getDriver() {
        return Driver.builder()
                .id(id)
                .firstName(NAME)
                .lastName(LAST_NAME)
                .username(EMAIL)
                .phoneNumber(PHONE_NUMBER)
                .password(PASSWORD)
                .build();
    }

    public static DriverCreateDto getCreateDto() {
        return new DriverCreateDto(NAME, LAST_NAME, EMAIL, PASSWORD, PHONE_NUMBER);
    }

    public static Driver getUpdateDriver() {
        return Driver.builder()
                .id(id)
                .firstName(NAME)
                .lastName(LAST_NAME)
                .username(EMAIL)
                .phoneNumber(NEW_PHONE_NUMBER)
                .password(PASSWORD)
                .build();
    }

    public static DriverReadDto getUpdateDto() {
        return new DriverReadDto(NAME, LAST_NAME, EMAIL);
    }

    public static DriverReadDto getReadDto() {
        return new DriverReadDto(NAME, LAST_NAME, EMAIL);
    }
}
