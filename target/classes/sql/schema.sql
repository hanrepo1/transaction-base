CREATE TABLE IF NOT EXISTS users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(100) NOT NULL UNIQUE,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    password VARCHAR(255) NOT NULL,
    profile_image VARCHAR(255),
    balance BIGINT DEFAULT 0
);

CREATE TABLE IF NOT EXISTS transactions (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    invoice_number VARCHAR(50) NOT NULL UNIQUE,
    transaction_type VARCHAR(50) NOT NULL,
    description VARCHAR(255),
    total_amount BIGINT NOT NULL,
    created_on TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS banner (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    banner_name VARCHAR(50) NOT NULL,
    banner_image VARCHAR(255) NOT NULL,
    description VARCHAR(255) NOT NULL
);

INSERT INTO banner (banner_name, banner_image, description)
VALUES
    ('Banner 1', 'https://minio.nutech-integrasi.com/take-home-test/banner/Banner-1.png', 'Lorem'),
    ('Banner 2', 'https://minio.nutech-integrasi.com/take-home-test/banner/Banner-2.png', 'Lorem Ipsum'),
    ('Banner 3', 'https://minio.nutech-integrasi.com/take-home-test/banner/Banner-3.png', 'Lorem Ipsum Dolor'),
    ('Banner 4', 'https://minio.nutech-integrasi.com/take-home-test/banner/Banner-4.png', 'Lorem Ipsum Dolor sit'),
    ('Banner 5', 'https://minio.nutech-integrasi.com/take-home-test/banner/Banner-5.png', 'Lorem Ipsum Dolor sit amet')
ON DUPLICATE KEY UPDATE
                     banner_name = VALUES(banner_name),
                     banner_image = VALUES(banner_image),
                     description = VALUES(description);

CREATE TABLE IF NOT EXISTS services (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    service_code VARCHAR(50) NOT NULL UNIQUE,
    service_name VARCHAR(50) NOT NULL,
    service_icon VARCHAR(255),
    service_tariff BIGINT DEFAULT 0
);

INSERT INTO services (service_code, service_name, service_icon, service_tariff)
VALUES
    ('PAJAK', 'Pajak PBB', '', 40000),
    ('PLN', 'Listrik', '', 10000),
    ('PDAM', 'PDAM Berlangganan', '', 40000),
    ('PULSA', 'Pulsa', '', 40000),
    ('PGN', 'PGN Berlangganan', '', 50000),
    ('MUSIK', 'Musik Berlangganan', '', 50000),
    ('TV', 'TV Berlangganan', '', 50000),
    ('PAKET_DATA', 'Paket Data', '', 50000),
    ('VOUCHER_GAME', 'Voucher Game', '', 100000),
    ('VOUCHER_MAKANAN', 'Voucher Makanan', '', 100000),
    ('QURBAN', 'Qurban', '', 200000),
    ('ZAKAT', 'Zakat', '', 300000)
ON DUPLICATE KEY UPDATE
                     service_name = VALUES(service_name),
                     service_icon = VALUES(service_icon),
                     service_tariff = VALUES(service_tariff);