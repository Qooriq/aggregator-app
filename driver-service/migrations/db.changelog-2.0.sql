--liquibase formatted sql

--changeset Anton Kazoluski:1
INSERT INTO drivers(id, first_name, last_name, username, password)
VALUES ('4ebba608-6315-447e-9bf5-4e20da6fb0b0', 'Anton', 'Kazlouski', 'pochta@gmail.com', '1234'),
       ('82582967-375f-4188-b3fc-e0390b9f22b4', 'Zhenya', 'Mihas', 'zhenya@gmail.com', '1234'),
       ('9a9952bf-e389-4e40-b00f-b66876f42aec', 'Kirill', 'Sopronyuk', 'kirill@gmail.com', '1234'),
       ('80e4f4a9-c183-4516-b986-303d35dad718', 'Victor', 'Gaika', 'victor@gmail.com', '1234');
