--liquibase formatted sql

--changeset Anton Kazlouski:1
ALTER TABLE rides
ADD COLUMN payment_method VARCHAR(16) DEFAULT 'CARD'