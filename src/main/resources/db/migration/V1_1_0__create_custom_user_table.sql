CREATE TABLE customer
(
    id       BIGINT AUTO_INCREMENT NOT NULL,
    email    VARCHAR(255)          NOT NULL,
    password VARCHAR(255)          NOT NULL,
    `role`   VARCHAR(50)          NOT NULL,
    CONSTRAINT pk_customer PRIMARY KEY (id)
);

ALTER TABLE customer
    ADD CONSTRAINT uc_customer_email UNIQUE (email);

INSERT INTO customer (email, password, role)
    VALUES ('user@email.com', '{bcrypt}$2a$12$.eaRIKyqmV5OS6ycI5uW.O3iYfjeAyPk7DJwTekVGk3PbXxr3y3DS', 'read'),
           ('admin@email.com', '{bcrypt}$2a$12$.eaRIKyqmV5OS6ycI5uW.O3iYfjeAyPk7DJwTekVGk3PbXxr3y3DS', 'admin');
