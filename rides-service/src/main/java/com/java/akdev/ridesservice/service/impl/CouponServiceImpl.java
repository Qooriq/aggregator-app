package com.java.akdev.ridesservice.service.impl;

import com.java.akdev.ridesservice.dto.CouponDto;
import com.java.akdev.ridesservice.enumeration.Order;
import com.java.akdev.ridesservice.enumeration.SortField;
import com.java.akdev.ridesservice.exception.EntityNotFound;
import com.java.akdev.ridesservice.mapper.CouponMapper;
import com.java.akdev.ridesservice.repository.CouponRepository;
import com.java.akdev.ridesservice.service.CouponService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CouponServiceImpl implements CouponService {

    private final CouponRepository couponRepository;
    private final CouponMapper couponMapper;
    private final static String EXCEPTION = "message.couponNotFound.error";

    @Override
    public Page<CouponDto> findAll(Integer page, Integer size, SortField sortField, Order order) {
        Sort.Direction dir = getDirection(order);
        var req = PageRequest.of(page - 1,
                size,
                dir,
                sortField.getName());
        return couponRepository.findAll(req)
                .map(couponMapper::toCouponDto);
    }

    @Override
    public CouponDto findById(Long id) {
        return couponRepository.findById(id)
                .map(couponMapper::toCouponDto)
                .orElseThrow(() -> new EntityNotFound(EXCEPTION));
    }

    @Override
    public CouponDto create(CouponDto dto) {
        var coupon = couponMapper.toCoupon(dto);
        return couponMapper.toCouponDto(
                couponRepository.save(coupon));
    }

    @Override
    public CouponDto update(Long id, CouponDto dto) {
        return couponRepository.findById(id)
                .map(ride -> couponMapper.updateCoupon(ride, dto))
                .map(couponRepository::save)
                .map(couponMapper::toCouponDto)
                .orElseThrow(() -> new EntityNotFound(EXCEPTION));
    }

    @Override
    public void delete(Long id) {

    }

    private Sort.Direction getDirection(Order order) {
        return order == Order.ASC ? Sort.Direction.ASC : Sort.Direction.DESC;
    }
}
