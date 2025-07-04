-- Suppression de l'ancienne table authorities
DROP TABLE authorities;

CREATE TABLE authorities (
    id int PRIMARY KEY AUTO_INCREMENT,
    customer_id BIGINT NOT NULL,
    name VARCHAR(50) NOT NULL,
    FOREIGN KEY fk_customer_authority (customer_id) REFERENCES customer(id)
);