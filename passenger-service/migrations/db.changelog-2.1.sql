--liquibase formatted sql

--changeset Anton Kazoluski:1
ALTER TABLE passenger_rating
DROP CONSTRAINT passenger_rating_driver_id_key;

--changeset Anton Kazoluski:2
INSERT INTO passenger_rating(passenger_id, review, driver_id) VALUES
('4ebba608-6315-447e-9bf5-4e20da6fb0b0', 5, '4ebba608-6315-447e-9bf5-4e20da6fb0b0'),
('4ebba608-6315-447e-9bf5-4e20da6fb0b0', 4, '4ebba608-6315-447e-9bf5-4e20da6fb0b0'),
('4ebba608-6315-447e-9bf5-4e20da6fb0b0', 4, '4ebba608-6315-447e-9bf5-4e20da6fb0b0'),
('4ebba608-6315-447e-9bf5-4e20da6fb0b0', 4, '4ebba608-6315-447e-9bf5-4e20da6fb0b0'),
('4ebba608-6315-447e-9bf5-4e20da6fb0b0', 3, '4ebba608-6315-447e-9bf5-4e20da6fb0b0'),
('4ebba608-6315-447e-9bf5-4e20da6fb0b0', 5, '4ebba608-6315-447e-9bf5-4e20da6fb0b0'),
('4ebba608-6315-447e-9bf5-4e20da6fb0b0', 5, '4ebba608-6315-447e-9bf5-4e20da6fb0b0')