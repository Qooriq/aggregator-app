package com.java.akdev.passengerservice.repository;

import com.java.akdev.passengerservice.entity.Passenger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PassengerRepository extends JpaRepository<Passenger, UUID> {
}
