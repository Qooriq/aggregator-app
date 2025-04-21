package com.java.akdev.reviewservice.controller;

import com.java.akdev.reviewservice.dto.ReviewCreateDto;
import com.java.akdev.reviewservice.dto.ReviewReadDto;
import com.java.akdev.reviewservice.dto.ReviewResponse;
import com.java.akdev.reviewservice.enumeration.Receiver;
import com.java.akdev.reviewservice.enumeration.SortField;
import com.java.akdev.reviewservice.service.ReviewService;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("api/v1/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @GetMapping
    public ResponseEntity<Page<ReviewReadDto>> findAll(@RequestParam @Min(1) Integer page,
                                                       @RequestParam @Min(1) Integer size,
                                                       @RequestParam SortField sortField,
                                                       @RequestParam Sort.Direction order) {
        return ResponseEntity.status(200)
                .body(reviewService.findAll(page, size, sortField, order));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReviewReadDto> findById(@PathVariable @Min(1) Long id) {
        return ResponseEntity.status(200)
                .body(reviewService.findById(id));
    }

    @PostMapping
    public ResponseEntity<ReviewReadDto> create(@RequestBody @Validated ReviewCreateDto dto) {
        return ResponseEntity.status(201)
                .body(reviewService.createReview(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReviewReadDto> update(@PathVariable @Min(1) Long id,
                                                @RequestBody @Validated ReviewCreateDto dto) {
        return ResponseEntity.status(200)
                .body(reviewService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        reviewService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/user-review/{id}")
    public ResponseEntity<ReviewResponse> getUserReview(@PathVariable UUID id, Receiver receiver) {
        return ResponseEntity.status(200)
                .body(reviewService.findAverageRating(id, receiver));
    }


}
