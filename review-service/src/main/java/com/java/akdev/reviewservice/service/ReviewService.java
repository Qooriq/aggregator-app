package com.java.akdev.reviewservice.service;

import com.java.akdev.reviewservice.dto.ReviewResponseAmount;
import com.java.akdev.commonmodels.dto.ReviewResponse;
import com.java.akdev.reviewservice.dto.ReviewCreateDto;
import com.java.akdev.reviewservice.enumeration.Order;
import com.java.akdev.reviewservice.enumeration.Receiver;
import com.java.akdev.reviewservice.enumeration.SortField;
import java.util.UUID;
import org.springframework.data.domain.Page;

public interface ReviewService {

    Page<ReviewResponse> findAll(Integer page, Integer size, SortField field, Order direction);

    ReviewResponse findById(Long id);

    ReviewResponse createReview(ReviewCreateDto dto);

    ReviewResponse update(Long id, ReviewCreateDto dto);

    void delete(Long id);

    ReviewResponseAmount findAverageRating(UUID user, Receiver receiver);
}
