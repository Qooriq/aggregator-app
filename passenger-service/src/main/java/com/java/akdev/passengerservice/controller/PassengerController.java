package com.java.akdev.passengerservice.controller;

import com.java.akdev.passengerservice.dto.PassengerCreateDto;
import com.java.akdev.passengerservice.dto.PassengerReadDto;
import com.java.akdev.passengerservice.service.PassengerRatingService;
import com.java.akdev.passengerservice.service.PassengerService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("api/v1/passenger")
@RequiredArgsConstructor
public class PassengerController {

    private final PassengerService passengerService;
    private final PassengerRatingService passengerRatingService;

    @GetMapping
    public ResponseEntity<Page<PassengerReadDto>> findAll(@RequestParam Integer page,
                                                          @RequestParam Integer size) {
        return ResponseEntity.status(200).body(passengerService.findAll(page, size));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PassengerReadDto> findById(@PathVariable UUID id) {
        return ResponseEntity.status(200).body(passengerService.findPassengerById(id));
    }

    @PostMapping
    public ResponseEntity<PassengerReadDto> create(@RequestBody PassengerCreateDto dto) {
        return ResponseEntity.status(201).body(passengerService.createPassenger(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PassengerReadDto> update(@PathVariable UUID id,
                                                   @RequestBody PassengerCreateDto dto) {
        return ResponseEntity.status(200).body(passengerService.updatePassenger(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        passengerService.deletePassenger(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/passenger-rating/{id}")
    public ResponseEntity<Double> getPassengerRating(@PathVariable UUID id) {
        return ResponseEntity.status(200).body(passengerRatingService.getAvgRating(id));
    }
}
