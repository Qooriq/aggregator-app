--liquibase formatted sql

--changeset Anton Kazlouski:1
CREATE TABLE wallets(
    id BIGSERIAL PRIMARY KEY,
    card_number VARCHAR(16) NOT NULL,
    amount DECIMAL NOT NULL DEFAULT 0.0 CHECK (amount > 0.0),
    user_id UUID NOT NULL
);