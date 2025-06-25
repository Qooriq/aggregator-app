package com.java.akdev.driverservice.util;

import com.java.akdev.driverservice.dto.DriverCreateDto;
import com.java.akdev.commonmodels.dto.UserResponse;
import com.java.akdev.driverservice.entity.Driver;
import com.java.akdev.driverservice.enumeration.Order;
import com.java.akdev.driverservice.enumeration.SortField;
import lombok.experimental.UtilityClass;

import java.util.UUID;

@UtilityClass
public class TestSetUps {

    public static UUID id = UUID.fromString("4ebba608-6315-447e-9bf5-4e20da6fb0b0");
    public final static Integer DEFAULT_PAGE = 1;
    public final static Integer DEFAULT_PAGE_SIZE = 10;
    public final static SortField SORT_FIELD = SortField.ID;
    public final static Order ORDER = Order.ASC;
    private final static String NAME = "Anton";
    private final static String LAST_NAME = "Kazlouski";
    private final static String NEW_EMAIL = "agusha@gmail.com";
    private final static String EMAIL = "pochta@gmail.com";
    private final static String PHONE_NUMBER = "1234567890";
    private final static String NEW_PHONE_NUMBER = "1234567";
    private final static String PASSWORD = "1234";

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
        return new DriverCreateDto(NAME, LAST_NAME, NEW_EMAIL, PASSWORD, PHONE_NUMBER);
    }

    public static Driver getUpdateDriver() {
        return Driver.builder()
                .id(id)
                .firstName(NAME)
                .lastName(LAST_NAME)
                .username(NEW_EMAIL)
                .phoneNumber(NEW_PHONE_NUMBER)
                .password(PASSWORD)
                .build();
    }

    public static UserResponse getUpdateDto() {
        return new UserResponse(NAME, LAST_NAME, NEW_EMAIL);
    }

    public static UserResponse getReadDto() {
        return new UserResponse(NAME, LAST_NAME, EMAIL);
    }
}
