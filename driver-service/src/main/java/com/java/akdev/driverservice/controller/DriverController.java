package com.java.akdev.driverservice.controller;

import com.java.akdev.driverservice.dto.DriverCreateDto;
import com.java.akdev.driverservice.dto.DriverReadDto;
import com.java.akdev.driverservice.enumeration.Order;
import com.java.akdev.driverservice.enumeration.SortField;
import com.java.akdev.driverservice.service.DriverService;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("api/v1/drivers")
@RequiredArgsConstructor
public class DriverController {

    private final DriverService driverService;

    @GetMapping
    public ResponseEntity<Page<DriverReadDto>> findAll(@RequestParam @Min(1) Integer page,
                                                       @RequestParam @Min(1) Integer size,
                                                       @RequestParam SortField sortField,
                                                       @RequestParam Order order) {
        return ResponseEntity.status(200)
                .body(driverService.findAll(page, size, sortField, order));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DriverReadDto> findById(@PathVariable UUID id) {
        return ResponseEntity.status(200)
                .body(driverService.findDriverById(id));
    }

    @PostMapping
    public ResponseEntity<DriverReadDto> create(@Validated
                                                @RequestBody DriverCreateDto dto) {
        return ResponseEntity.status(201)
                .body(driverService.createDriver(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DriverReadDto> update(@PathVariable UUID id,
                                                @Validated @RequestBody DriverCreateDto dto) {
        return ResponseEntity.status(200)
                .body(driverService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<DriverReadDto> delete(@PathVariable UUID id) {
        driverService.deleteDriver(id);
        return ResponseEntity.noContent().build();
    }
}
