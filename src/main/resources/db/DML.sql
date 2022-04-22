-- DML
INSERT INTO user (email, password, username, date_of_birth, first_name, image_url, job_title, last_name, phone, city, state)
VALUES ('adminmail@mail.com','$2a$10$y5ynLMW3jNqMy1g9e3xMxuwc4iwIdzhwSx4LEJLYno8pV5qQ8OgSO','administrator','1992-01-12','Joris','http://localhost:8080/api/auth/image/user(2).png','administrator','Peterson','+37068600002','Vilnius','Lithuania');

INSERT INTO role (role)
VALUES ('ROLE_ADMIN');
INSERT INTO role (role)
VALUES ('ROLE_OWNER');
INSERT INTO role (role)
VALUES ('ROLE_USER');

INSERT INTO user_role (user_id, role_id)
VALUES ((SELECT id FROM user WHERE username = 'administrator'), (SELECT id FROM role WHERE role = 'ROLE_ADMIN'));