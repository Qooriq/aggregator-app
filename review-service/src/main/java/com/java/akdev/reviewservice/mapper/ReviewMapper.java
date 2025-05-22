package com.java.akdev.reviewservice.mapper;

import com.java.akdev.commonmodels.dto.ReviewResponse;
import com.java.akdev.reviewservice.config.MapperConfiguration;
import com.java.akdev.reviewservice.dto.ReviewCreateDto;
import com.java.akdev.reviewservice.entity.Review;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(config = MapperConfiguration.class)
public interface ReviewMapper {

    ReviewResponse toDto(Review review);

    Review toEntity(ReviewCreateDto dto);

    Review map(@MappingTarget Review review, ReviewCreateDto dto);
}
