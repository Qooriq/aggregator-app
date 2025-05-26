package com.java.akdev.passengerservice.kafka;

import com.java.akdev.commonmodels.dto.ReviewMessage;
import com.java.akdev.passengerservice.enumeration.ExceptionMessages;
import com.java.akdev.passengerservice.exception.PassengerNotFoundException;
import com.java.akdev.passengerservice.repository.PassengerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReviewConsumer {

    private final PassengerRepository passengerRepository;

    @KafkaListener(
            topics = "passenger-review-update",
            containerFactory = "listenerContainerFactory",
            groupId = "passenger-group"
    )
    public void listen(@Payload ReviewMessage review) {
        try {
            var passenger = passengerRepository.findById(review.userId())
                    .orElseThrow(() -> new PassengerNotFoundException(ExceptionMessages.PASSENGER_NOT_FOUND.getName()));
            passenger.setRating(review.review());

            passengerRepository.save(passenger);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
