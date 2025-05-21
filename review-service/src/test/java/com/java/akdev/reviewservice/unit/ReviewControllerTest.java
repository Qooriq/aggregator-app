package com.java.akdev.reviewservice.unit;

import com.java.akdev.reviewservice.controller.ReviewController;
import com.java.akdev.reviewservice.dto.ReviewCreateDto;
import com.java.akdev.reviewservice.dto.ReviewReadDto;
import com.java.akdev.reviewservice.service.ReviewService;
import com.java.akdev.reviewservice.util.TestSetUps;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ReviewControllerTest {

    @Mock
    private ReviewService reviewService;

    @InjectMocks
    private ReviewController reviewController;

    private Long id;
    private ReviewCreateDto reviewCreateDto;
    private ReviewReadDto reviewReadDto;

    @BeforeEach
    void setUp() {
        id = TestSetUps.id;
        reviewCreateDto = TestSetUps.getCreateDto();
        reviewReadDto = TestSetUps.getReadDto();
    }

    @Test
    void getReviewById() {
        when(reviewService.findById(id))
                .thenReturn(reviewReadDto);

        var review = reviewController.findById(id);
        assertThat(review.getBody()).isEqualTo(reviewReadDto);

        verify(reviewService).findById(id);
    }

    @Test
    void createReview() {
        when(reviewService.createReview(reviewCreateDto))
                .thenReturn(reviewReadDto);

        var review = reviewController.create(reviewCreateDto);
        assertThat(review.getBody()).isEqualTo(reviewReadDto);

        verify(reviewService).createReview(reviewCreateDto);
    }

    @Test
    void updateReview() {
        when(reviewService.update(id, reviewCreateDto))
                .thenReturn(reviewReadDto);

        var review = reviewController.update(id, reviewCreateDto);
        assertThat(review.getBody()).isEqualTo(reviewReadDto);

        verify(reviewService).update(id, reviewCreateDto);
    }

    @Test
    void deleteReview() {
        doNothing().when(reviewService).delete(id);

        reviewController.delete(id);

        verify(reviewService).delete(id);
    }
}
