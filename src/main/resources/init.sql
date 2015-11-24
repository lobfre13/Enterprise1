INSERT INTO User(email, password, role) VALUES('f@f.com', 'Passw0rd', 'STUDENT');
INSERT INTO User(email, password, role) VALUES('f@f.com', 'Passw0rd', 'STUDENT');
INSERT INTO User(email, password, role) VALUES('f@f.com', 'Passw0rd', 'STUDENT');
INSERT INTO User(email, password, role) VALUES('f@f.com', 'Passw0rd', 'STUDENT');
INSERT INTO User(email, password, role) VALUES('f@f.com', 'Passw0rd', 'STUDENT');
INSERT INTO User(email, password, role) VALUES('f@f.com', 'Passw0rd', 'STUDENT');

INSERT INTO Location(id, room, building) VALUES(1, '81', 'Galleriet');

INSERT INTO Subject(id, name, FK_LOCATION) VALUES(1, 'PG5600', 1);
INSERT INTO Subject(id, name) VALUES(2, 'PG5600');
INSERT INTO Subject(id, name) VALUES(3, 'PG5600');

INSERT INTO USR_SUB(users_id, subjects_id) VALUES(1, 3);
INSERT INTO USR_SUB(users_id, subjects_id) VALUES(2, 3);
INSERT INTO USR_SUB(users_id, subjects_id) VALUES(3, 3);
INSERT INTO USR_SUB(users_id, subjects_id) VALUES(4, 3);
INSERT INTO USR_SUB(users_id, subjects_id) VALUES(5, 3);
