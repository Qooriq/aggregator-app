package com.java.akdev.reviewservice.controller;

import com.java.akdev.commonmodels.dto.ReviewResponse;
import com.java.akdev.reviewservice.enumeration.Order;
import com.java.akdev.reviewservice.dto.ReviewCreateDto;
import com.java.akdev.reviewservice.dto.ReviewResponseAmount;
import com.java.akdev.commonmodels.enumeration.Receiver;
import com.java.akdev.reviewservice.enumeration.SortField;
import com.java.akdev.reviewservice.service.ReviewService;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("api/v1/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Page<ReviewResponse>> findAll(@RequestParam @Min(1) Integer page,
                                                       @RequestParam @Min(1) Integer size,
                                                       @RequestParam SortField sortField,
                                                       @RequestParam Order order) {
        return ResponseEntity.status(200)
                .body(reviewService.findAll(page, size, sortField, order));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReviewResponse> findById(@PathVariable @Min(1) Long id) {
        return ResponseEntity.status(200)
                .body(reviewService.findById(id));
    }

    @PostMapping
    public ResponseEntity<ReviewResponse> create(@RequestBody @Validated ReviewCreateDto dto) {
        return ResponseEntity.status(201)
                .body(reviewService.createReview(dto));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ReviewResponse> update(@PathVariable @Min(1) Long id,
                                                @RequestBody @Validated ReviewCreateDto dto) {
        return ResponseEntity.status(200)
                .body(reviewService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        reviewService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/user-review/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ReviewResponseAmount> getUserReview(@PathVariable UUID id, Receiver receiver) {
        return ResponseEntity.status(200)
                .body(reviewService.findAverageRating(id, receiver));
    }


}
