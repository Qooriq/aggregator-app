package com.java.akdev.passengerservice.repository;

import com.java.akdev.passengerservice.entity.PassengerRating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PassengerRatingRepository extends JpaRepository<PassengerRating, Long> {

    @Query("SELECT AVG(pr.review) FROM PassengerRating pr WHERE pr.passenger.id = :passenger_id")
    Double avgByReview(@Param("passenger_id") UUID passengerId);
}
