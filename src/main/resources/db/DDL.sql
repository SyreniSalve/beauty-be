-- DDL
CREATE TABLE user
(
    `id`       bigint PRIMARY KEY NOT NULL AUTO_INCREMENT,
    `email`    varchar(250)       NOT NULL UNIQUE,
    `password` varchar(120)       NOT NULL,
    `username` varchar(50)        NOT NULL UNIQUE,
    `date_of_birth` date DEFAULT NULL,
    `first_name` varchar(250) DEFAULT NULL,
    `image_url` varchar(255) DEFAULT NULL,
    `job_title` varchar(120) NOT NULL,
    `last_name` varchar(250) DEFAULT NULL,
    `phone` varchar(12) DEFAULT NULL,
    `city` varchar(255) DEFAULT NULL,
    `state` varchar(255) DEFAULT NULL
);

CREATE TABLE event
(
    `id`       bigint PRIMARY KEY NOT NULL AUTO_INCREMENT,
    `title`    varchar(250)       NULL,
    `start_time` timestamp        NULL,
    `end_time` timestamp          NULL,
    `location` varchar(255)       NULL,
    `color` varchar(120)          NULL,
    `group_id` bigint              NULL,
    `user_id` bigint             NULL,
    FOREIGN KEY (`user_id`) REFERENCES user (`id`)
);

CREATE TABLE role
(
    `id`   bigint PRIMARY KEY NOT NULL AUTO_INCREMENT,
    `role` varchar(20) UNIQUE
);

CREATE TABLE `user_role`
(
    `user_id` bigint NOT NULL,
    `role_id` bigint NOT NULL,
    UNIQUE (user_id, role_id),
    FOREIGN KEY (`user_id`) REFERENCES user (`id`),
    FOREIGN KEY (`role_id`) REFERENCES role (`id`)
);

CREATE TABLE `refresh_token`
(
    `id`          bigint PRIMARY KEY NOT NULL AUTO_INCREMENT,
    `expiry_date` datetime        NOT NULL,
    `token`       varchar(255)       NOT NULL UNIQUE,
    `user_id`     bigint             NOT NULL,
    FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;