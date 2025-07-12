--liquibase formatted sql

--changeset Anton Kazlouski:1
ALTER TABLE rides
    ALTER COLUMN driver_id DROP NOT NULL;

--changeset Anton Kazlouski:2
ALTER TABLE rides
 ADD COLUMN payment_method VARCHAR(16);

--changeset Anton Kazlouski:3 context:test
INSERT INTO rides(passenger_id, driver_id, driver_review_passenger, passenger_review_driver, start_location, end_location, start_time, end_time, ride_price, ride_status, payment_method)
VALUES
('4ebba608-6315-447e-9bf5-4e20da6fb0b0', null, null, null, '5.4 5.3', '5.0 5.9', '2025-04-21 01:12:54.431536', null, 79.95536530359746, 'COMPLETED', 'CARD'),
('4ebba608-6315-447e-9bf5-4e20da6fb0b0', null, null, null, '5.4 5.3', '5.0 5.9', '2025-04-21 07:01:22.802081', null, 10, 'COMPLETED', 'CARD'),
('1826829b-d77a-4908-b1b4-94cf5346a038', null, null, null, '5.4 5.3', '5.0 5.9', '2025-04-21 07:42:28.894832', null, 10, 'COMPLETED', 'CARD'),
('1826829b-d77a-4908-b1b4-94cf5346a038', null, null, null, '5.4 5.3', '5.0 5.9', '2025-04-21 08:09:00.748803', null, 10, 'COMPLETED', 'CARD')

