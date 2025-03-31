package com.java.akdev.passengerservice.mapper;

import com.java.akdev.passengerservice.config.MapperConfiguration;
import com.java.akdev.passengerservice.dto.PassengerCreateDto;
import com.java.akdev.passengerservice.dto.PassengerReadDto;
import com.java.akdev.passengerservice.entity.Passenger;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(config = MapperConfiguration.class)
public interface PassengerMapper {

    PassengerReadDto toReadDto(Passenger passenger);

    Passenger toPassenger(PassengerCreateDto dto);

    void map(@MappingTarget Passenger to, PassengerCreateDto dto);
}
