--liquibase formatted sql

--changeset Anton Kazlouski:1
ALTER TABLE drivers
    RENAME COLUMN passenger_status to driver_status;