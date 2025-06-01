CREATE TABLE IF NOT EXISTS `event` (
    `id` BIGINT NOT NULL AUTO_INCREMENT,
    `title` VARCHAR(100) NOT NULL,
    `description` VARCHAR(600) NOT NULL,
    `event_date` DATETIME NOT NULL,
    `address_street` VARCHAR(100),
    `address_neighborhood` VARCHAR(30),
    `address_city` VARCHAR(20),
    `address_state` VARCHAR(2),
    `address_cep` VARCHAR(8),
    `event_url` VARCHAR(150) NOT NULL,
    `event_image_url` VARCHAR(150) NOT NULL,
    `capacity` INT,
    `remote` BOOLEAN NOT NULL,
    PRIMARY KEY (`id`)
);