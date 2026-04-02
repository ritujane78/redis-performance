DROP TABLE IF EXISTS product;

CREATE TABLE product (
                         id BIGINT AUTO_INCREMENT PRIMARY KEY,
                         description VARCHAR(500),
                         price DECIMAL(10,2) NOT NULL
);
