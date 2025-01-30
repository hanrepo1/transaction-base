CREATE TABLE users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(100) NOT NULL UNIQUE,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    password VARCHAR(255) NOT NULL,
    profile_image VARCHAR(255),
    balance BIGINT DEFAULT 0
);

CREATE TABLE transactions (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    invoice_number VARCHAR(50) NOT NULL UNIQUE,
    transaction_type VARCHAR(50) NOT NULL,
    description VARCHAR(255),
    total_amount BIGINT NOT NULL,
    created_on TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

CREATE TABLE banner (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    banner_name VARCHAR(50) NOT NULL,
    banner_image VARCHAR(50) NOT NULL,
    description VARCHAR(255) NOT NULL
);

CREATE TABLE services (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    service_code VARCHAR(50) NOT NULL,
    service_name VARCHAR(50) NOT NULL,
    service_icon VARCHAR(255),
    service_tariff BIGINT DEFAULT 0
);

