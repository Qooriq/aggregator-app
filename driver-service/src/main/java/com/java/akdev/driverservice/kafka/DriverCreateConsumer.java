package com.java.akdev.driverservice.kafka;

import com.java.akdev.commonmodels.dto.UserRegistrationCreateDto;
import com.java.akdev.driverservice.entity.Driver;
import com.java.akdev.driverservice.repository.DriverRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DriverCreateConsumer {

    private final DriverRepository driverRepository;

    @KafkaListener(
            topics = "driver-create-topic",
            containerFactory = "listenerContainerFactory",
            groupId = "driver-group"
    )
    public void listen(@Payload UserRegistrationCreateDto dto) {
        try {
            var passenger = Driver.builder()
                    .id(dto.id())
                    .firstName(dto.firstName())
                    .lastName(dto.lastName())
                    .username(dto.username())
                    .password(dto.password())
                    .phoneNumber(dto.phoneNumber())
                    .build();

            driverRepository.save(passenger);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
