package com.java.akdev.passengerservice.controller;

import com.java.akdev.commonmodels.dto.UserResponse;
import com.java.akdev.passengerservice.dto.PassengerCreateDto;
import com.java.akdev.passengerservice.enumeration.Order;
import com.java.akdev.passengerservice.enumeration.SortField;
import com.java.akdev.passengerservice.service.PassengerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("api/v1/passengers")
@RequiredArgsConstructor
@Slf4j
public class PassengerController {

    private final PassengerService passengerService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Page<UserResponse>> findAll(@RequestParam Integer page,
                                                      @RequestParam Integer size,
                                                      @RequestParam SortField sortField,
                                                      @RequestParam Order order) {
        return ResponseEntity.ok()
                .body(passengerService.findAll(page, size, sortField, order));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserResponse> findById(@PathVariable UUID id) {
        log.info("Find passenger by id: {}", id);
        return ResponseEntity.ok()
                .body(passengerService.findPassengerById(id));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserResponse> create(@Validated
                                               @RequestBody PassengerCreateDto dto) {
        return ResponseEntity.status(201)
                .body(passengerService.createPassenger(dto));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or (hasRole('PASSENGER') and #id.toString() == authentication.principal.claims['sub'])")
    public ResponseEntity<UserResponse> update(@PathVariable UUID id,
                                               @Validated @RequestBody PassengerCreateDto dto) {
        return ResponseEntity.ok()
                .body(passengerService.updatePassenger(id, dto));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or (hasRole('PASSENGER') and #id.toString() == authentication.principal.claims['sub'])")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        passengerService.deletePassenger(id);
        return ResponseEntity.noContent().build();
    }
}
