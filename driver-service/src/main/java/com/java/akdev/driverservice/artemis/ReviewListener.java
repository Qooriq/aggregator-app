package com.java.akdev.driverservice.artemis;

import com.java.akdev.commonmodels.dto.ReviewMessage;
import com.java.akdev.driverservice.enumeration.ExceptionMessages;
import com.java.akdev.driverservice.exception.DriverNotFoundException;
import com.java.akdev.driverservice.repository.DriverRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReviewListener {

    private final DriverRepository driverRepository;

    @JmsListener(destination = "review-drivers-update",
            containerFactory = "jmsListenerContainerFactory")
    public void receiveReview(ReviewMessage review) {
        var driver = driverRepository.findById(review.userId())
                .orElseThrow(() -> new DriverNotFoundException(ExceptionMessages.DRIVER_NOT_FOUND.getName()));

        driver.setRating(review.review());

        driverRepository.save(driver);
    }
}
