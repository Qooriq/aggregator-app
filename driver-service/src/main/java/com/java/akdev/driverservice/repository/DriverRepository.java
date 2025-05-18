package com.java.akdev.driverservice.repository;

import com.java.akdev.driverservice.entity.Driver;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface DriverRepository extends JpaRepository<Driver, UUID> {

    boolean existsByUsername(String username);

    boolean existsByPhoneNumber(String phoneNumber);
}
