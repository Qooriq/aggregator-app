package com.java.akdev.reviewservice.service.impl;

import com.java.akdev.commonmodels.dto.ReviewMessage;
import com.java.akdev.commonmodels.dto.ReviewResponse;
import com.java.akdev.commonmodels.enumeration.Receiver;
import com.java.akdev.reviewservice.artemis.ReviewArtemisProducer;
import com.java.akdev.reviewservice.config.AppConfiguration;
import com.java.akdev.reviewservice.dto.ReviewCreateDto;
import com.java.akdev.reviewservice.dto.ReviewResponseAmount;
import com.java.akdev.reviewservice.entity.Review;
import com.java.akdev.reviewservice.enumeration.Order;
import com.java.akdev.reviewservice.enumeration.SortField;
import com.java.akdev.reviewservice.exception.ReviewNotFoundException;
import com.java.akdev.reviewservice.kafka.ReviewKafkaSender;
import com.java.akdev.reviewservice.mapper.ReviewMapper;
import com.java.akdev.reviewservice.repository.ReviewRepository;
import com.java.akdev.reviewservice.service.ReviewService;
import jakarta.persistence.EntityManagerFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final ReviewMapper reviewMapper;
    private final ReviewKafkaSender kafkaProducer;
    private final ReviewArtemisProducer artemisProducer;
    private final AppConfiguration appConfiguration;
    private final EntityManagerFactory entityManagerFactory;

    private final static String ERROR_MESSAGE = "ReviewController.entity.notFound";

    public Page<ReviewResponse> findAll(Integer page, Integer size, SortField field, Order order) {
        Sort.Direction dir = getDirection(order);
        var req = PageRequest.of(page,
                size,
                dir,
                field.getName());
        return reviewRepository.findAll(req)
                .map(reviewMapper::toDto);
    }

    public ReviewResponse findById(Long id) {
        return reviewRepository.findById(id)
                .map(reviewMapper::toDto)
                .orElseThrow(() -> new ReviewNotFoundException(ERROR_MESSAGE));
    }

    @Transactional
    public ReviewResponse createReview(ReviewCreateDto dto) {
        var entity = reviewMapper.toEntity(dto);
        var res = reviewRepository.save(entity);
        return reviewMapper.toDto(res);
    }

    @Transactional
    public ReviewResponse update(Long id, ReviewCreateDto dto) {
        var review = reviewRepository.findById(id)
                .orElseThrow(() -> new ReviewNotFoundException(ERROR_MESSAGE));
        review = reviewMapper.map(review, dto);
        var res = reviewRepository.save(review);
        return reviewMapper.toDto(res);
    }

    @Transactional
    public void delete(Long id) {
        reviewRepository.deleteById(id);
    }

    public ReviewResponseAmount findAverageRating(UUID user, Receiver receiver) {
        return new ReviewResponseAmount(reviewRepository.findAllByUser(
                        user, receiver, PageRequest.of(
                                appConfiguration.page(),
                                appConfiguration.size()
                        )
                ).stream()
                .mapToDouble(Double::doubleValue)
                .average()
                .orElse(0.0));
    }

    @Scheduled(cron = "${app.chrono-update}")
    public void sendReviewsToUsers() {
        var passengers = reviewRepository.findAllByReceiver(Receiver.PASSENGER)
                .stream().map(Review::getReceiverId)
                .distinct()
                .toList();

        var drivers = reviewRepository.findAllByReceiver(Receiver.DRIVER)
                .stream().map(Review::getReceiverId)
                .distinct()
                .toList();

        passengers.forEach(
                passengerId -> {
                    var review = findAverageRating(passengerId);
                    kafkaProducer.sendReview(
                            new ReviewMessage(passengerId, review.review())
                    );
                }
        );

        drivers.forEach(
                driverId -> {
                    var review = findAverageRating(driverId);
                    artemisProducer.sendReview(
                            new ReviewMessage(driverId, review.review())
                    );
                }
        );
    }

    private ReviewResponseAmount findAverageRating(UUID user) {
        return new ReviewResponseAmount(reviewRepository.findAllByReceiverId(user)
                .stream()
                .map(Review::getReview)
                .mapToDouble(Short::shortValue)
                .average()
                .orElse(5.0));
    }

    private Sort.Direction getDirection(Order order) {
        return order == Order.ASC ? Sort.Direction.ASC : Sort.Direction.DESC;
    }
}

