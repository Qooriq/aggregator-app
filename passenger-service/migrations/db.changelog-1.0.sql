--liquibase formatted sql

--changeset Anton Kazlouski:1
CREATE TABLE passengers(
                      id UUID PRIMARY KEY,
                      first_name VARCHAR(64) NOT NULL,
                      last_name VARCHAR(64) NOT NULL,
                      username VARCHAR(64) NOT NULL UNIQUE,
                      password VARCHAR(64) NOT NULL,
                      phone_number VARCHAR(16) UNIQUE
);

CREATE TABLE passenger_rating(
    id BIGSERIAL PRIMARY KEY,
    passenger_id UUID NOT NULL REFERENCES passengers(id),
    review BIGINT
)