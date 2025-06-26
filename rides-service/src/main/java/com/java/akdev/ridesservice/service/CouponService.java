package com.java.akdev.ridesservice.service;

import com.java.akdev.ridesservice.dto.CouponDto;
import com.java.akdev.ridesservice.enumeration.Order;
import com.java.akdev.ridesservice.enumeration.SortField;
import org.springframework.data.domain.Page;

public interface CouponService {

    Page<CouponDto> findAll(Integer page, Integer size, SortField sortField, Order order);

    CouponDto findById(Long id);

    CouponDto create(CouponDto dto);

    CouponDto update(Long id, CouponDto dto);

    void delete(Long id);
}
