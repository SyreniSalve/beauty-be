-- DML
INSERT INTO user (email, password,username) VALUES ('adminmail@mail.com', 'encriptedadminpassword','administrator');

INSERT INTO role (role) VALUES ('ROLE_ADMIN');
INSERT INTO role (role) VALUES ('ROLE_OWNER');
INSERT INTO role (role) VALUES ('ROLE_USER');

INSERT INTO user_role (user_id, role_id) VALUES ((SELECT id FROM user WHERE username='administrator'), (SELECT id FROM role WHERE role='ROLE_ADMIN'));

