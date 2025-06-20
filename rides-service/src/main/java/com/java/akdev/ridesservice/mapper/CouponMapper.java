package com.java.akdev.ridesservice.mapper;

import com.java.akdev.ridesservice.config.MapperConfiguration;
import com.java.akdev.ridesservice.dto.CouponDto;
import com.java.akdev.ridesservice.entity.Coupon;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(config = MapperConfiguration.class)
public interface CouponMapper {

    CouponDto toCouponDto(Coupon coupon);

    Coupon toCoupon(CouponDto dto);

    Coupon updateCoupon(@MappingTarget Coupon ride, CouponDto dto);
}
