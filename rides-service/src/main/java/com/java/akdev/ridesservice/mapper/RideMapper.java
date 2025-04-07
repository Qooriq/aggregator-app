package com.java.akdev.ridesservice.mapper;

import com.java.akdev.ridesservice.config.MapperConfiguration;
import com.java.akdev.ridesservice.dto.RideCreateDto;
import com.java.akdev.ridesservice.dto.RideReadDto;
import com.java.akdev.ridesservice.dto.RideUpdateDto;
import com.java.akdev.ridesservice.entity.Ride;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(config = MapperConfiguration.class)
public interface RideMapper {

    RideReadDto toRideReadDto(Ride ride);

    Ride toRide(RideCreateDto dto);

    void map(@MappingTarget Ride ride, RideCreateDto dto);

    void map(@MappingTarget Ride ride, RideUpdateDto dto);
}
