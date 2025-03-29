package com.java.akdev.passengerservice.service;

import com.java.akdev.passengerservice.dto.PassengerCreateDto;
import com.java.akdev.passengerservice.dto.PassengerRatingDto;
import com.java.akdev.passengerservice.entity.PassengerRating;
import com.java.akdev.passengerservice.exception.PassengerNotFoundException;
import com.java.akdev.passengerservice.exception.PassengerRatingNotFoundException;
import com.java.akdev.passengerservice.repository.PassengerRatingRepository;
import com.java.akdev.passengerservice.repository.PassengerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PassengerRatingService {

    private final PassengerRatingRepository passengerRatingRepository;
    private final PassengerRepository passengerRepository;

    @Value("${passenger-rating.limit}")
    private Integer ratingLimit;
    @Value("${passenger-rating.page-number}")
    private Integer pageNumber;

    @Transactional(readOnly = true)
    public Page<PassengerRatingDto> getAllPassengerRatings(Integer page, Integer size) {
        return passengerRatingRepository.findAll(PageRequest.of(page - 1, size))
                .map(PassengerRatingService::passengerToDto);
    }

    @Transactional(readOnly = true)
    public PassengerRatingDto findPassengerRatingById(Long id) {
        return passengerRatingRepository.findById(id)
                .map(PassengerRatingService::passengerToDto)
                .orElseThrow(PassengerRatingNotFoundException::new);
    }

    @Transactional
    public PassengerRatingDto updatePassengerRating(Long id, PassengerRatingDto dto) {
        return passengerRatingRepository.findById(id)
                .map(rating -> {
                    PassengerRating.builder()
                        .id(rating.getId())
                        .review(dto.review())
                        .passenger(passengerRepository.findById(dto.passengerId())
                                .orElseThrow(PassengerNotFoundException::new))
                        .driverId(dto.driverId())
                        .build();
                    passengerRatingRepository.save(rating);
                    return passengerToDto(rating);
                })
                .orElseThrow(PassengerRatingNotFoundException::new);
    }

    @Transactional
    public PassengerRatingDto createPassengerRating(PassengerRatingDto dto) {
        return passengerToDto(passengerRatingRepository.save(PassengerRating.builder()
                .passenger(passengerRepository
                        .findById(dto.passengerId())
                        .orElseThrow(PassengerNotFoundException::new))
                .driverId(dto.driverId())
                .review(dto.review())
                .build()));
    }

    @Transactional
    public void deletePassengerRating(Long id) {
        var rating = passengerRatingRepository
                .findById(id)
                .orElseThrow(PassengerRatingNotFoundException::new);
        passengerRatingRepository.delete(rating);
    }

    @Transactional(readOnly = true)
    public Double getAvgRating(UUID passengerId) {
        return passengerRatingRepository
                .findAllByPassenger(passengerId, PageRequest.of(pageNumber, ratingLimit))
                .stream()
                .mapToDouble(Double::doubleValue)
                .average()
                .orElse(0.0);
    }

    private static PassengerRatingDto passengerToDto(PassengerRating passenger) {
        return new PassengerRatingDto(passenger.getPassenger().getId(),
                passenger.getDriverId(), passenger.getReview());
    }
}
