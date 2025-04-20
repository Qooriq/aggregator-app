--liquibase formatted sql

--changeset Anton Kazoluski:1
ALTER TABLE passengers
ADD COLUMN rating DECIMAL DEFAULT 5.0 NOT NULL;