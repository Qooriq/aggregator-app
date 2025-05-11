package com.java.akdev.reviewservice.unit;

import com.java.akdev.reviewservice.dto.ReviewCreateDto;
import com.java.akdev.reviewservice.dto.ReviewReadDto;
import com.java.akdev.reviewservice.entity.Review;
import com.java.akdev.reviewservice.exception.ReviewNotFoundException;
import com.java.akdev.reviewservice.mapper.ReviewMapper;
import com.java.akdev.reviewservice.repository.ReviewRepository;
import com.java.akdev.reviewservice.service.impl.ReviewServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.java.akdev.reviewservice.util.TestSetUps;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReviewServiceImplTest {

    @Mock
    private ReviewMapper reviewMapper;
    @Mock
    private ReviewRepository reviewRepository;
    @InjectMocks
    private ReviewServiceImpl reviewService;

    private Review review;
    private Review updateReview;
    private ReviewCreateDto reviewCreateDto;
    private ReviewReadDto reviewUpdateDto;
    private ReviewReadDto reviewReadDto;
    private Long id;

    @BeforeEach
    void setUp() {
        review = TestSetUps.getReview();
        id = TestSetUps.id;
        reviewCreateDto = TestSetUps.getCreateDto();
        reviewReadDto = TestSetUps.getReadDto();
        reviewUpdateDto = TestSetUps.getUpdateDto();
        updateReview = TestSetUps.getUpdateReview();
    }

    @Test
    @DisplayName("find review by id")
    void findReviewById() {

        when(reviewRepository.findById(id))
                .thenReturn(Optional.of(review));
        when(reviewMapper.toDto(review))
                .thenReturn(reviewReadDto);

        var rev = reviewService.findById(id);

        assertThat(rev)
                .isEqualTo(reviewReadDto);

        verify(reviewMapper).toDto(review);
        verify(reviewRepository).findById(id);
    }

    @Test
    @DisplayName("review not found exception")
    void findReviewNotFoundById() {

        when(reviewRepository.findById(id))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() ->
                reviewService.findById(id)
        ).isInstanceOf(ReviewNotFoundException.class);

        verify(reviewRepository).findById(id);
    }

    @Test
    @DisplayName("create review")
    void createReview() {
        when(reviewRepository.save(review))
                .thenReturn(review);
        when(reviewMapper.toEntity(reviewCreateDto))
                .thenReturn(review);
        when(reviewMapper.toDto(review))
                .thenReturn(reviewReadDto);

        var rev = reviewService.createReview(reviewCreateDto);

        assertThat(rev)
                .isEqualTo(reviewReadDto);

        verify(reviewRepository).save(review);
        verify(reviewMapper).toEntity(reviewCreateDto);
        verify(reviewMapper).toDto(review);
    }

    @Test
    @DisplayName("update review by id")
    void update() {
        when(reviewRepository.findById(id))
                .thenReturn(Optional.of(review));
        when(reviewRepository.save(review))
                .thenReturn(updateReview);
        when(reviewMapper.toDto(updateReview))
                .thenReturn(reviewUpdateDto);
        when(reviewMapper.map(review, reviewCreateDto))
                .thenReturn(updateReview);

        var revDto = reviewService.update(id, reviewCreateDto);

        assertThat(revDto)
                .isEqualTo(reviewUpdateDto);

        verify(reviewRepository).findById(id);
        verify(reviewRepository).save(review);
        verify(reviewMapper).toDto(updateReview);
        verify(reviewMapper).map(review, reviewCreateDto);
    }

    @Test
    @DisplayName("delete review by id")
    void deleteReview() {
        doNothing().when(reviewRepository).deleteById(id);

        reviewService.delete(id);

        verify(reviewRepository).deleteById(id);
    }
}