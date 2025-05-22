package com.java.akdev.reviewservice.service;

import com.java.akdev.reviewservice.dto.ReviewReadDto;
import com.java.akdev.reviewservice.dto.ReviewResponse;
import com.java.akdev.reviewservice.dto.ReviewCreateDto;
import com.java.akdev.reviewservice.enumeration.Order;
import com.java.akdev.reviewservice.enumeration.Receiver;
import com.java.akdev.reviewservice.enumeration.SortField;
import java.util.UUID;
import org.springframework.data.domain.Page;

public interface ReviewService {

    Page<ReviewReadDto> findAll(Integer page, Integer size, SortField field, Order direction);

    ReviewReadDto findById(Long id);

    ReviewReadDto createReview(ReviewCreateDto dto);

    ReviewReadDto update(Long id, ReviewCreateDto dto);

    void delete(Long id);

    ReviewResponse findAverageRating(UUID user, Receiver receiver);
}
