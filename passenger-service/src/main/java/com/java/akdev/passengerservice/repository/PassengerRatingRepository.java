package com.java.akdev.passengerservice.repository;

import com.java.akdev.passengerservice.entity.PassengerRating;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PassengerRatingRepository extends JpaRepository<PassengerRating, Long> {

    @Query("SELECT pr.review FROM PassengerRating pr WHERE pr.passenger.id = :passengerId ORDER BY pr.id DESC")
    List<Double> findAllByPassenger(UUID passengerId, Pageable pageable);
}
