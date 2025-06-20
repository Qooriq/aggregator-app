package com.java.akdev.ridesservice.repository;

import com.java.akdev.ridesservice.entity.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CouponRepository extends JpaRepository<Coupon, Long> {

    boolean existsByCoupon(String coupon);

    Coupon findByCoupon(String coupon);
}
