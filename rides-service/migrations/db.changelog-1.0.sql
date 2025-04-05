--liquibase formatted sql

--changeset Anton Kazlouski:1
CREATE TABLE rides(
    id BIGSERIAL PRIMARY KEY,
    passenger_id UUID NOT NULL,
    driver_id UUID NOT NULL,
    driver_review_passenger BIGINT,
    passenger_review_driver BIGINT,
    start_location VARCHAR(128) NOT NULL,
    end_location VARCHAR(128) NOT NULL,
    start_time TIMESTAMP NOT NULL,
    end_time TIMESTAMP,
    ride_price DECIMAL,
    ride_status VARCHAR(16)
)