package com.java.akdev.ridesservice.repository;

import com.java.akdev.ridesservice.entity.Ride;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RideRepository extends JpaRepository<Ride, Long> {
}
