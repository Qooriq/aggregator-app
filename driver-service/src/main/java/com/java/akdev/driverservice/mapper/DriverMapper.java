package com.java.akdev.driverservice.mapper;

import com.java.akdev.commonmodels.dto.UserResponse;
import com.java.akdev.driverservice.configuration.MapperConfiguration;
import com.java.akdev.driverservice.dto.DriverCreateDto;
import com.java.akdev.driverservice.entity.Driver;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(config = MapperConfiguration.class)
public interface DriverMapper {

    UserResponse toUserResponse(Driver driver);

    Driver toDriver(DriverCreateDto driverCreateDto);

    void updateDriver(@MappingTarget Driver to, DriverCreateDto from);
}
