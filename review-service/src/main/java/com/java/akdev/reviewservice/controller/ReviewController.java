package com.java.akdev.reviewservice.controller;

import com.java.akdev.reviewservice.dto.ReviewCreateDto;
import com.java.akdev.reviewservice.dto.ReviewReadDto;
import com.java.akdev.reviewservice.enumeration.Receiver;
import com.java.akdev.reviewservice.service.ReviewService;
import com.java.akdev.reviewservice.util.SortType;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("v1/api/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @GetMapping
    public ResponseEntity<Page<ReviewReadDto>> findAll(@RequestParam Integer page,
                                                       @RequestParam Integer size,
                                                       @RequestBody SortType sortType) {
        return ResponseEntity.status(200)
                .body(reviewService.findAll(page, size, sortType));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReviewReadDto> findById(@PathVariable Long id) {
        return ResponseEntity.status(200)
                .body(reviewService.findById(id));
    }

    @PostMapping
    public ResponseEntity<ReviewReadDto> create(@RequestBody @Validated ReviewCreateDto dto) {
        return ResponseEntity.status(201)
                .body(reviewService.createReview(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReviewReadDto> update(@PathVariable Long id, @RequestBody @Validated ReviewCreateDto dto) {
        return ResponseEntity.status(200)
                .body(reviewService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        reviewService.delete(id);
        return ResponseEntity.noContent().build();
    }

    //TODO: change receiver in the future
    @GetMapping("/user-review/{id}")
    public ResponseEntity<Double> getUserReview(@PathVariable UUID id) {
        return ResponseEntity.status(200)
                .body(reviewService.findAverageRating(id, Receiver.PASSENGER));
    }


}
