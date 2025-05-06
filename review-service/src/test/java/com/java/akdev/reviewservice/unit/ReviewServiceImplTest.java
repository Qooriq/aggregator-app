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
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
    @DisplayName("find driver by id")
    void findDriverById() {

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
    @DisplayName("driver not found exception")
    void findDriverNotFoundById() {

        when(reviewRepository.findById(id))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() ->
                reviewService.findById(id)
        ).isInstanceOf(ReviewNotFoundException.class);

        verify(reviewRepository).findById(id);
    }

    @Test
    @DisplayName("create review")
    void createDriver() {
        when(reviewRepository.save(review))
                .thenReturn(review);
        when(reviewMapper.toEntity(reviewCreateDto))
                .thenReturn(review);
        when(reviewMapper.toDto(review))
                .thenReturn(reviewReadDto);

        var driv = reviewService.createReview(reviewCreateDto);

        assertThat(driv)
                .isEqualTo(reviewReadDto);

        verify(reviewRepository).save(review);
        verify(reviewMapper).toEntity(reviewCreateDto);
        verify(reviewMapper).toDto(review);
    }

    @Test
    @DisplayName("update driver by id")
    void update() {
        when(reviewRepository.findById(id))
                .thenReturn(Optional.of(review));
        when(reviewRepository.save(review))
                .thenReturn(updateReview);
        when(reviewMapper.toDto(updateReview))
                .thenReturn(reviewUpdateDto);

        var drivDto = reviewService.update(id, reviewCreateDto);

        assertThat(drivDto)
                .isEqualTo(reviewUpdateDto);

        verify(reviewRepository).findById(id);
        verify(reviewRepository).save(review);
        verify(reviewMapper).toDto(updateReview);
    }

    @Test
    @DisplayName("delete driver by id")
    void deleteDriver() {
        when(reviewRepository.findById(id))
                .thenReturn(Optional.of(review));

        reviewService.delete(id);

        verify(reviewRepository).findById(id);
    }
}