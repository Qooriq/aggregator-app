--liquibase formatted sql

--changeset Anton Kazoluski:1
ALTER TABLE drivers
    ADD COLUMN rating DECIMAL DEFAULT 5.0 NOT NULL;