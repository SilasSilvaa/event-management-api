CREATE TABLE IF NOT EXISTS `user` (
    `id` bigint NOT NULL AUTO_INCREMENT,
    `name` varchar(100) NOT NULL,
    `last_name` varchar(100) NOT NULL,
    `email` varchar(100) NOT NULL UNIQUE,
    `gender` ENUM('M', 'F') NOT NULL,
    PRIMARY KEY (`id`)
);