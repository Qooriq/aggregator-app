package com.java.akdev.authservice.kafka;

import com.java.akdev.authservice.dto.UserRegistration;
import com.java.akdev.authservice.enumeration.Role;
import com.java.akdev.commonmodels.dto.UserRegistrationCreateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ReviewKafkaProducer implements ReviewKafkaSender {

    private final KafkaTemplate<Long, Object> kafkaTemplate;
    @Value("${spring.kafka.passenger-create-topic}")
    private String passengerCreateTopic;
    @Value("${spring.kafka.driver-create-topic}")
    private String driverCreateTopic;


    @Override
    public void sendPassengerCreate(UserRegistrationCreateDto dto, Role role) {
        if (role.equals(Role.PASSENGER)) {
            kafkaTemplate.send(
                    passengerCreateTopic,
                    dto
            );
        } else {
            kafkaTemplate.send(
                    driverCreateTopic,
                    dto
            );
        }
    }

}