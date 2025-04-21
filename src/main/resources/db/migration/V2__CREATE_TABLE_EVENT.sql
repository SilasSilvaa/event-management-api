CREATE TABLE IF NOT EXISTS `event` (
    `id` BIGINT NOT NULL AUTO_INCREMENT,
    `title` VARCHAR(100) NOT NULL,
    `description` VARCHAR(600) NOT NULL,
    `event_date` DATETIME NOT NULL,
    `address_street` VARCHAR(100) NOT NULL,
    `address_neighborhood` VARCHAR(30) NOT NULL,
    `address_city` VARCHAR(20) NOT NULL,
    `address_state` VARCHAR(2) NOT NULL,
    `address_cep` VARCHAR(8) NOT NULL,
    `event_url` VARCHAR(150) NOT NULL,
    `event_image_url` VARCHAR(150) NOT NULL,
    `capacity` INT,
    `remote` BOOLEAN NOT NULL,
    PRIMARY KEY (`id`)
);