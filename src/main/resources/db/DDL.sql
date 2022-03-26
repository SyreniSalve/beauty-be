-- DDL
CREATE TABLE `user` (
                        `id` bigint PRIMARY KEY NOT NULL AUTO_INCREMENT,
                        `email` varchar(250) NOT NULL UNIQUE,
                        `password` varchar(120) NOT NULL,
                        `username` varchar(50) NOT NULL UNIQUE
);

CREATE TABLE `role` (
                        `id` bigint PRIMARY KEY NOT NULL AUTO_INCREMENT,
                        `role` varchar(20) UNIQUE
);

CREATE TABLE `user_role` (
                             `user_id` bigint,
                             `role_id` bigint
);

ALTER TABLE `user` ADD FOREIGN KEY (`id`) REFERENCES `user_role` (`user_id`);

ALTER TABLE `role` ADD FOREIGN KEY (`id`) REFERENCES `user_role` (`role_id`);
