-- DML
INSERT INTO user (id, email, password) VALUES ('1', 'adminmail@mail.com', 'adminpassword');
INSERT INTO user (id, email, password) VALUES ('2', 'owner1mail@mail.com', 'owner1password');
INSERT INTO user (id, email, password) VALUES ('3', 'owner2mail@mail.com', 'owner2password');
INSERT INTO user (id, email, password) VALUES ('4', 'owner3mail@mail.com', 'owner3password');
INSERT INTO user (id, email, password) VALUES ('5', 'owner4mail@mail.com', 'owner4password');
INSERT INTO user (id, email, password) VALUES ('6', 'usermail@mail.com', 'userpassword');

INSERT INTO role (id, role) VALUES ('1', 'admin');
INSERT INTO role (id, role) VALUES ('2', 'owner');
INSERT INTO role (id, role) VALUES ('3', 'user');

INSERT INTO user_role (user_id, role_id) VALUES ('1', '1');
INSERT INTO user_role (user_id, role_id) VALUES ('2', '2');
INSERT INTO user_role (user_id, role_id) VALUES ('3', '2');
INSERT INTO user_role (user_id, role_id) VALUES ('4', '2');
INSERT INTO user_role (user_id, role_id) VALUES ('5', '2');
INSERT INTO user_role (user_id, role_id) VALUES ('6', '3');

