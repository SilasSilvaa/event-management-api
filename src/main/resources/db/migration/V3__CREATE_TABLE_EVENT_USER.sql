CREATE TABLE IF NOT EXISTS `event_user` (
    `event_id` BIGINT NOT NULL,
    `user_id` BIGINT NOT NULL,
    PRIMARY KEY (`event_id`, `user_id`),
    FOREIGN KEY (`event_id`) REFERENCES `event`(`id`) ON DELETE CASCADE,
    FOREIGN KEY (`user_id`) REFERENCES `user`(`id`) ON DELETE CASCADE
);