package com.java.akdev.passengerservice.kafka;

import com.java.akdev.passengerservice.dto.ReviewResponse;
import com.java.akdev.passengerservice.exception.PassengerNotFoundException;
import com.java.akdev.passengerservice.repository.PassengerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ReviewConsumer {

    private final PassengerRepository passengerRepository;

    @KafkaListener(
            topics = "passenger-review-update",
            containerFactory = "listenerContainerFactory",
            groupId = "passenger-group"
    )
    public void listen(@Payload ReviewResponse review) {
        try {
            var passenger = passengerRepository.findById(UUID.fromString(review.userId()))
                    .orElseThrow(() ->
                            new PassengerNotFoundException(
                            "PassengerController.passenger.notFound")
                    );

            passenger.setRating(review.review());

            passengerRepository.save(passenger);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
