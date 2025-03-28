package com.java.akdev.passengerservice.repository;

import com.java.akdev.passengerservice.entity.PassengerRating;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PassengerRatingRepository extends JpaRepository<PassengerRating, Long> {

    @Query("SELECT pr.review FROM PassengerRating pr WHERE pr.passenger.id = :passenger_id ORDER BY pr.id DESC")
    List<Double> findAllByPassenger(@Param("passenger_id") UUID passengerId, Pageable pageable);

    default Double findAverageOfLastElements(UUID passengerId) {
        List<Double> lastNValues = findAllByPassenger(passengerId, PageRequest.of(0, 50));
        return lastNValues.stream()
                .mapToDouble(Double::doubleValue)
                .average()
                .orElse(0.0);
    }
}
