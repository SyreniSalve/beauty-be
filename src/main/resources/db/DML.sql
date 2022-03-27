-- DML
INSERT INTO user (email, password,username) VALUES ('adminmail@mail.com', 'encriptedadminpassword','administrator');

INSERT INTO role (role) VALUES ('ROLE_admin');
INSERT INTO role (role) VALUES ('ROLE_owner');
INSERT INTO role (role) VALUES ('ROLE_user');

INSERT INTO user_role (user_id, role_id) VALUES ('SELECT id FROM user WHERE username='admininistrator'', 'SELECT id FROM role WHERE role='admin'');

