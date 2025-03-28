package com.java.akdev.passengerservice.repository;

import com.java.akdev.passengerservice.entity.PassengerRating;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PassengerRatingRepository extends JpaRepository<PassengerRating, Long> {
}
