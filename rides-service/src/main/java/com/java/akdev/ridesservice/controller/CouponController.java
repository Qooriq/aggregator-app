package com.java.akdev.ridesservice.controller;

import com.java.akdev.commonmodels.dto.RideResponse;
import com.java.akdev.ridesservice.dto.CouponDto;
import com.java.akdev.ridesservice.dto.RideCreateDto;
import com.java.akdev.ridesservice.dto.RideUpdateDto;
import com.java.akdev.ridesservice.enumeration.Order;
import com.java.akdev.ridesservice.enumeration.SortField;
import com.java.akdev.ridesservice.service.CouponService;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/coupons")
@RequiredArgsConstructor
public class CouponController {

    private final CouponService couponService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Page<CouponDto>> findAll(@RequestParam @Min(value = 1) Integer page,
                                                   @RequestParam @Min(value = 1) Integer size,
                                                   @RequestParam SortField sortField,
                                                   @RequestParam Order order) {
        return ResponseEntity.status(200)
                .body(couponService.findAll(page, size, sortField, order));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CouponDto> findById(@PathVariable @Min(1) Long id) {
        return ResponseEntity.status(200)
                .body(couponService.findById(id));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CouponDto> create(@RequestBody CouponDto dto) {
        return ResponseEntity.status(201)
                .body(couponService.create(dto));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CouponDto> update(@PathVariable @Min(1) Long id, @RequestBody CouponDto dto) {
        return ResponseEntity.status(200)
                .body(couponService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable @Min(1) Long id) {
        couponService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
