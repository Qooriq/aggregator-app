--liquibase formatted sql

--changeset Anton Kazlouski:1
ALTER TABLE reviews
    RENAME COLUMN passenger_id TO receiver_id;

--changeset Anton Kazlouski:2
ALTER TABLE reviews
    RENAME COLUMN driver_id TO reviewer_id;

--changeset Anton Kazlouski:3
INSERT INTO reviews(receiver_id, reviewer_id, comment, review, receiver_of_review, ride_id)
VALUES
('82582967-375f-4188-b3fc-e0390b9f22b4', '4ebba608-6315-447e-9bf5-4e20da6fb0b0', 'ha', 5, 'PASSENGER', 1),
('4ebba608-6315-447e-9bf5-4e20da6fb0b0', '82582967-375f-4188-b3fc-e0390b9f22b4', 'ha', 4, 'DRIVER', 1),
('9a9952bf-e389-4e40-b00f-b66876f42aec', '80e4f4a9-c183-4516-b986-303d35dad718', 'ha', 5, 'DRIVER', 2),
('80e4f4a9-c183-4516-b986-303d35dad718', '9a9952bf-e389-4e40-b00f-b66876f42aec', 'ha', 5, 'PASSENGER', 2)