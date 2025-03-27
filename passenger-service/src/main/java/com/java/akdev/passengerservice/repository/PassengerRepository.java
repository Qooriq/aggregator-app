package com.java.akdev.passengerservice.repository;

import com.java.akdev.passengerservice.entity.Passenger;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PassengerRepository extends JpaRepository<Passenger, UUID> {
}
