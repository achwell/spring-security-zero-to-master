DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS authorities;
DROP TABLE IF EXISTS customer;
CREATE TABLE users
(
    id       INT         NOT NULL AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(45) NOT NULL,
    password VARCHAR(255) NOT NULL,
    enabled  INT         NOT NULL
);

CREATE TABLE authorities
(
    id        int         NOT NULL AUTO_INCREMENT PRIMARY KEY,
    username  varchar(45) NOT NULL,
    authority varchar(45) NOT NULL
);


CREATE TABLE customer
(
    id    int         NOT NULL AUTO_INCREMENT PRIMARY KEY,
    email varchar(255) NOT NULL,
    pwd   varchar(255) NOT NULL,
    role  varchar(45) NOT NULL
);

INSERT INTO users (username, password, enabled)
VALUES ('happy', '12345', '1');

INSERT INTO authorities (username, authority)
VALUES ('happy', 'write');

INSERT INTO customer (email, pwd, role)
VALUES ('johndoe@example.com', '54321', 'admin');

INSERT INTO customer (email, pwd, role)
VALUES ('happy@example.com', '$2y$12$oRRbkNfwuR8ug4MlzH5FOeui.//1mkd.RsOAJMbykTSupVy.x/vb2', 'admin');