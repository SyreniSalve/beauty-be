-- DDL
CREATE TABLE user (
    `id` bigint PRIMARY KEY NOT NULL AUTO_INCREMENT,
    `email` varchar(250) NOT NULL UNIQUE,
    `password` varchar(120) NOT NULL,
    `username` varchar(50) NOT NULL UNIQUE
);

CREATE TABLE role (
    `id` bigint PRIMARY KEY NOT NULL AUTO_INCREMENT,
    `role` varchar(20) UNIQUE
);

CREATE TABLE `user_role` (
                             `user_id` bigint NOT NULL ,
                             `role_id` bigint NOT NULL,
                             UNIQUE(user_id, role_id),
                             FOREIGN KEY (`user_id`) REFERENCES user (`id`),
                             FOREIGN KEY (`role_id`) REFERENCES role (`id`)
);
