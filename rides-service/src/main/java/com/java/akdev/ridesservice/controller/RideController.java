package com.java.akdev.ridesservice.controller;

import com.java.akdev.commonmodels.dto.RideResponse;
import com.java.akdev.ridesservice.dto.RideCreateDto;
import com.java.akdev.ridesservice.dto.RideUpdateDto;
import com.java.akdev.ridesservice.enumeration.Order;
import com.java.akdev.ridesservice.enumeration.SortField;
import com.java.akdev.ridesservice.service.RideService;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/rides")
@RequiredArgsConstructor
public class RideController {

    private final RideService rideService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Page<RideResponse>> findAll(@RequestParam @Min(value = 1) Integer page,
                                                      @RequestParam @Min(value = 1) Integer size,
                                                      @RequestParam SortField sortField,
                                                      @RequestParam Order order) {
        return ResponseEntity.status(200)
                .body(rideService.findAll(page, size, sortField, order));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<RideResponse> findById(@PathVariable @Min(1) Long id) {
        return ResponseEntity.status(200)
                .body(rideService.findById(id));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('PASSENGER')")
    public ResponseEntity<RideResponse> create(@RequestBody RideCreateDto dto) {
        return ResponseEntity.status(201)
                .body(rideService.create(dto));
    }

    @PutMapping("/end-ride/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('DRIVER')")
    public ResponseEntity<RideResponse> endRide(@PathVariable @Min(1) Long id) {
        return ResponseEntity.status(200)
                .body(rideService.endRide(id));
    }

    @PutMapping("/take-ride/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('DRIVER')")
    public ResponseEntity<RideResponse> takeRide(@PathVariable Long id) {
        return ResponseEntity.status(200)
                .body(rideService.startRide(id));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<RideResponse> update(@PathVariable @Min(1) Long id, @RequestBody RideUpdateDto dto) {
        return ResponseEntity.status(200)
                .body(rideService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable @Min(1) Long id) {
        rideService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/driver")
    @PreAuthorize("hasRole('ADMIN') or hasRole('DRIVER')")
    public ResponseEntity<RideResponse> findFirstAvailableRide() {
        return ResponseEntity.status(200)
                .body(rideService.findFirstAvailableRide());
    }
}
