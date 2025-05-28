package com.java.akdev.ridesservice.repository;

import com.java.akdev.ridesservice.entity.Ride;
import com.java.akdev.ridesservice.enumeration.RideStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RideRepository extends JpaRepository<Ride, Long> {

    Page<Ride> findAllByStatus(RideStatus status, Pageable pageable);
}
