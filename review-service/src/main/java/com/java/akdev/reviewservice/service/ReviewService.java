package com.java.akdev.reviewservice.service;

import com.java.akdev.reviewservice.dto.ReviewCreateDto;
import com.java.akdev.reviewservice.dto.ReviewReadDto;
import com.java.akdev.reviewservice.enumeration.Receiver;
import com.java.akdev.reviewservice.exception.ReviewNotFoundException;
import com.java.akdev.reviewservice.mapper.ReviewMapper;
import com.java.akdev.reviewservice.repository.ReviewRepository;
import com.java.akdev.reviewservice.util.SortType;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final ReviewMapper reviewMapper;

    @Value("${passenger-rating.page-number}")
    private Integer page;
    @Value("${passenger-rating.limit}")
    private Integer size;

    public Page<ReviewReadDto> findAll(Integer page, Integer size, SortType sortType) {
        return reviewRepository
                .findAll(PageRequest.of(page,
                        size,
                        sortType.getOrder(),
                        sortType.getSortField().getName())
                ).map(reviewMapper::toDto);
    }

    public ReviewReadDto findById(Long id) {
        return reviewRepository.findById(id)
                .map(reviewMapper::toDto)
                .orElseThrow(ReviewNotFoundException::new);
    }

    public ReviewReadDto createReview(ReviewCreateDto dto) {
        return reviewMapper
                .toDto(reviewRepository
                        .save(reviewMapper.toEntity(dto)));
    }

    public ReviewReadDto update(Long id, ReviewCreateDto dto) {
        return reviewRepository.findById(id)
                .map(review -> {
                    reviewMapper.map(review, dto);
                    return review;
                })
                .map(reviewRepository::save)
                .map(reviewMapper::toDto)
                .orElseThrow(ReviewNotFoundException::new);
    }

    public void delete(Long id) {
        reviewRepository.deleteById(id);
    }

    public Double findAverageRating(UUID user, Receiver receiver) {
        return reviewRepository.findAllByUser(
                        user, receiver, PageRequest.of(page, size)
                ).stream()
                .mapToDouble(Double::doubleValue)
                .average()
                .orElse(0.0);
    }
}
