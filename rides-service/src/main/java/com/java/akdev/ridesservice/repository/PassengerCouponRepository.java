package com.java.akdev.ridesservice.repository;

import com.java.akdev.ridesservice.entity.PassengerCoupons;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PassengerCouponRepository extends JpaRepository<PassengerCoupons, Long> {

    boolean existsByCouponNameAndPassengerId(String couponName, UUID passengerId);
}
