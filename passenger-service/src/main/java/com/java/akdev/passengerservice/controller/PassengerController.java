package com.java.akdev.passengerservice.controller;

import com.java.akdev.passengerservice.dto.PassengerCreateDto;
import com.java.akdev.passengerservice.dto.PassengerReadDto;
import com.java.akdev.passengerservice.enumeration.Order;
import com.java.akdev.passengerservice.enumeration.SortField;
import com.java.akdev.passengerservice.service.PassengerRatingService;
import com.java.akdev.passengerservice.service.PassengerService;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("api/v1/passengers")
@RequiredArgsConstructor
public class PassengerController {

    private final PassengerService passengerService;
    private final PassengerRatingService passengerRatingService;

    @GetMapping
    public ResponseEntity<Page<PassengerReadDto>> findAll(@RequestParam @Min(1) Integer page,
                                                          @RequestParam @Min(1) Integer size,
                                                          @RequestParam SortField sortField,
                                                          @RequestParam Order order) {
        return ResponseEntity.status(200)
                .body(passengerService.findAll(page, size, sortField, order));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PassengerReadDto> findById(@PathVariable UUID id) {
        return ResponseEntity.status(200)
                .body(passengerService.findPassengerById(id));
    }

    @PostMapping
    public ResponseEntity<PassengerReadDto> create(@Validated
                                                   @RequestBody PassengerCreateDto dto) {
        return ResponseEntity.status(201)
                .body(passengerService.createPassenger(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PassengerReadDto> update(@PathVariable UUID id,
                                                   @Validated @RequestBody PassengerCreateDto dto) {
        return ResponseEntity.status(200)
                .body(passengerService.updatePassenger(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        passengerService.deletePassenger(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/passenger-rating/{id}")
    public ResponseEntity<Double> getPassengerRating(@PathVariable UUID id) {
        return ResponseEntity.status(200)
                .body(passengerRatingService.getAvgRating(id));
    }
}
