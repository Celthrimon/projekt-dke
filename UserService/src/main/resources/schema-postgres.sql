DROP TABLE IF EXISTS users;
CREATE TABLE users
(
    username   VARCHAR(255) PRIMARY KEY,
    password   VARCHAR(255),
    attributes VARCHAR(2048)
);