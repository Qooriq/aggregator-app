package com.java.akdev.ridesservice.dto;

public record PointDto(
        Double lat,
        Double lon
) {

    @Override
    public String toString() {
        return lat + " " + lon;
    }
}
