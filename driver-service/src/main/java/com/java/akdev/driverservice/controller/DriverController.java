package com.java.akdev.driverservice.controller;

import com.java.akdev.driverservice.dto.DriverCreateDto;
import com.java.akdev.driverservice.dto.DriverReadDto;
import com.java.akdev.driverservice.service.DriverService;
import com.java.akdev.driverservice.util.SortType;
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
    public ResponseEntity<Page<DriverReadDto>> findAll(@RequestBody SortType sortType,
                                                       @RequestParam Integer page,
                                                       @RequestParam Integer size) {
        return ResponseEntity.status(200)
                .body(driverService.findAllDrivers(sortType, page, size));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DriverReadDto> findById(@PathVariable UUID id) {
        return ResponseEntity.status(200)
                .body(driverService.findDriverById(id));
    }

    @PostMapping
    public ResponseEntity<DriverReadDto> create(@Validated
                                                @RequestBody
                                                DriverCreateDto dto) {
        return ResponseEntity.status(201)
                .body(driverService.createDriver(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DriverReadDto> update(@PathVariable UUID id,
                                                @Validated
                                                @RequestBody DriverCreateDto dto) {
        return ResponseEntity.status(200)
                .body(driverService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<DriverReadDto> delete(@PathVariable UUID id) {
        driverService.deleteDriver(id);
        return ResponseEntity.noContent().build();
    }
}
