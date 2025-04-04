--liquibase formatted sql

--changeset Anton Kazlouski:1
CREATE TABLE reviews(
    id BIGSERIAL PRIMARY KEY,
    passenger_id UUID NOT NULL,
    driver_id UUID NOT NULL,
    comment VARCHAR(512),
    review SMALLINT NOT NULL,
    receiver_of_review VARCHAR(16) NOT NULL,
    ride_id  BIGINT
);