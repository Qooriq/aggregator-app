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
    public ResponseEntity<Page<UserResponse>> findAll(@RequestParam Integer page,
                                                      @RequestParam Integer size,
                                                      @RequestParam SortField sortField,
                                                      @RequestParam Order order) {
        return ResponseEntity.ok()
                .body(passengerService.findAll(page, size, sortField, order));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> findById(@PathVariable UUID id) {
        log.info("Find passenger by id: {}", id);
        return ResponseEntity.ok()
                .body(passengerService.findPassengerById(id));
    }

    @PostMapping
    public ResponseEntity<UserResponse> create(@Validated
                                               @RequestBody PassengerCreateDto dto) {
        return ResponseEntity.status(201)
                .body(passengerService.createPassenger(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> update(@PathVariable UUID id,
                                               @Validated @RequestBody PassengerCreateDto dto) {
        return ResponseEntity.ok()
                .body(passengerService.updatePassenger(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        passengerService.deletePassenger(id);
        return ResponseEntity.noContent().build();
    }
}
