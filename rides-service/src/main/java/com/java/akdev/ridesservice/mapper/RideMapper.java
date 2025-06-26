package com.java.akdev.ridesservice.mapper;

import com.java.akdev.commonmodels.dto.RideResponse;
import com.java.akdev.ridesservice.config.MapperConfiguration;
import com.java.akdev.ridesservice.dto.PointDto;
import com.java.akdev.ridesservice.dto.RideCreateDto;
import com.java.akdev.ridesservice.dto.RideUpdateDto;
import com.java.akdev.ridesservice.entity.Ride;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(config = MapperConfiguration.class)
public interface RideMapper {

    RideResponse toRideResponse(Ride ride);

    Ride toRide(RideCreateDto dto);

    Ride updateRide(@MappingTarget Ride ride, RideUpdateDto dto);

    default String map(PointDto value){
        return value.lat() + " " + value.lon();
    }
}
