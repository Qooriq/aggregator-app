--liquibase formatted sql

--changeset Anton Kazlouski:1
ALTER TABLE rides
    ALTER COLUMN start_time DROP NOT NULL;