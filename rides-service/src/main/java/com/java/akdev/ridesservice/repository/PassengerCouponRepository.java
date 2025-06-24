package com.java.akdev.ridesservice.repository;

import com.java.akdev.ridesservice.entity.PassengerCoupons;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface PassengerCouponRepository extends JpaRepository<PassengerCoupons, Long> {

    @Query("SELECT COUNT(pc) > 0 FROM PassengerCoupons pc " +
           "WHERE pc.coupon.coupon = :couponCode AND pc.passengerId = :passengerId")
    boolean existsByCouponAndPassengerId(@Param("couponCode") String couponCode,
                                         @Param("passengerId") UUID passengerId);
}
