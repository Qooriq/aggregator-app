package com.java.akdev.passengerservice.kafka;

import com.java.akdev.commonmodels.dto.ReviewMessage;
import com.java.akdev.commonmodels.dto.UserRegistrationCreateDto;
import com.java.akdev.passengerservice.dto.PassengerCreateDto;
import com.java.akdev.passengerservice.entity.Passenger;
import com.java.akdev.passengerservice.enumeration.ExceptionMessages;
import com.java.akdev.passengerservice.exception.PassengerNotFoundException;
import com.java.akdev.passengerservice.mapper.PassengerMapper;
import com.java.akdev.passengerservice.repository.PassengerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PassengerCreateConsumer {

    private final PassengerRepository passengerRepository;

    @KafkaListener(
            topics = "passenger-create-topic",
            containerFactory = "listenerContainerFactory",
            groupId = "passenger-group"
    )
    public void listen(@Payload UserRegistrationCreateDto dto) {
        try {
            var passenger = Passenger.builder()
                    .id(dto.id())
                    .firstName(dto.firstName())
                    .lastName(dto.lastName())
                    .username(dto.username())
                    .password(dto.password())
                    .phoneNumber(dto.phoneNumber())
                    .build();

            passengerRepository.save(passenger);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
