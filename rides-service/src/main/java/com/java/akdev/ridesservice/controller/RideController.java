package com.java.akdev.ridesservice.controller;

import com.java.akdev.ridesservice.dto.RideCreateDto;
import com.java.akdev.ridesservice.dto.RideReadDto;
import com.java.akdev.ridesservice.dto.RideUpdateDto;
import com.java.akdev.ridesservice.enumeration.SortField;
import com.java.akdev.ridesservice.service.RideService;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/rides")
@RequiredArgsConstructor
public class RideController {

    private final RideService rideService;

    @GetMapping
    public ResponseEntity<Page<RideReadDto>> findAll(@RequestParam @Min(value = 1) Integer page,
                                                     @RequestParam @Min(value = 1) Integer size,
                                                     @RequestParam SortField sortField,
                                                     @RequestParam Sort.Direction order) {
        return ResponseEntity.status(200)
                .body(rideService.findAll(page, size, sortField, order));
    }

    @GetMapping("/{id}")
    public ResponseEntity<RideReadDto> findById(@PathVariable @Min(1) Long id) {
        return ResponseEntity.status(200)
                .body(rideService.findById(id));
    }

    @PostMapping
    public ResponseEntity<RideReadDto> create(@RequestBody RideCreateDto dto) {
        return ResponseEntity.status(201)
                .body(rideService.create(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<RideReadDto> update(@PathVariable @Min(1) Long id, @RequestBody RideUpdateDto dto) {
        return ResponseEntity.status(200)
                .body(rideService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable @Min(1) Long id) {
        rideService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
