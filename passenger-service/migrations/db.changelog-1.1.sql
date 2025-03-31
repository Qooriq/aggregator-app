--liquibase formatted sql

--changeset Anton Kazlouski:1
ALTER TABLE passenger_rating
ADD COLUMN driver_id UUID UNIQUE NOT NULL