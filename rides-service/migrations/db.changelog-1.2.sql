--liquibase formatted sql

--changeset Anton Kazlouski:1
ALTER TABLE rides
    ALTER COLUMN start_time DROP NOT NULL;

--changeset Anton Kazlouski:2
ALTER TABLE rides
    ADD COLUMN ride_type VARCHAR(16) NOT NULL DEFAULT 'ECONOMY';

--changeset Anton Kazlouski:3
CREATE TABLE coupons(
    id BIGSERIAL PRIMARY KEY,
    coupon VARCHAR(16) UNIQUE NOT NULL ,
    discount DECIMAL NOT NULL
);

--changeset Anton Kazlouski:4
CREATE TABLE passengers_coupons(
    id BIGSERIAL PRIMARY KEY,
    coupon_name VARCHAR(16) REFERENCES coupons(coupon),
    passenger_id UUID
)
