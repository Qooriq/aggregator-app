package com.java.akdev.authservice.kafka;

import com.java.akdev.authservice.enumeration.Role;
import com.java.akdev.commonmodels.dto.UserRegistrationCreateDto;

public interface ReviewKafkaSender {

    void sendPassengerCreate(UserRegistrationCreateDto dto, Role role);
}
