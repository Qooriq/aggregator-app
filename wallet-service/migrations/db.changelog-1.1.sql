--liquibase formatted sql

--changeset Anton Kazlouski:1
INSERT INTO wallets(card_number, amount, user_id) VALUES
('12345678', 120.0, '4ebba608-6315-447e-9bf5-4e20da6fb0b0'),
('87654321', 133.0, '82582967-375f-4188-b3fc-e0390b9f22b4'),
('47389372', 10000, '9a9952bf-e389-4e40-b00f-b66876f42aec')