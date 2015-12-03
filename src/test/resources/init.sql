INSERT INTO User(email, password, USER_ROLE) VALUES('f@f.com', 'Passw0rd', 'STUDENT');
INSERT INTO User(email, password, USER_ROLE) VALUES('f@f.com', 'Passw0rd', 'STUDENT');
INSERT INTO User(email, password, USER_ROLE) VALUES('f@f.com', 'Passw0rd', 'STUDENT');
INSERT INTO User(email, password, USER_ROLE) VALUES('f@f.com', 'Passw0rd', 'STUDENT');
INSERT INTO User(email, password, USER_ROLE) VALUES('f@f.com', 'Passw0rd', 'STUDENT');
INSERT INTO User(email, password, USER_ROLE) VALUES('f@f.com', 'Passw0rd', 'STUDENT');

INSERT INTO Location(id, room, building) VALUES(1, '81', 'Galleriet');

INSERT INTO Subject(id, name, FK_LOCATION) VALUES(1, 'PG5600', 1);
INSERT INTO Subject(id, name) VALUES(2, 'PG5600');
INSERT INTO Subject(id, name) VALUES(3, 'PG5600');

INSERT INTO USR_SUB(users_id, subjects_id) VALUES(1, 3);
INSERT INTO USR_SUB(users_id, subjects_id) VALUES(2, 3);
INSERT INTO USR_SUB(users_id, subjects_id) VALUES(3, 3);
INSERT INTO USR_SUB(users_id, subjects_id) VALUES(4, 3);
INSERT INTO USR_SUB(users_id, subjects_id) VALUES(5, 3);

INSERT INTO Event(id, title, description, EVENT_TYPE, FK_SUBJECT) VALUES (1, 'title', 'desc', 'LECTURE', 1);
INSERT INTO EVENT_DETAILS(starttime, endtime, id) VALUES (parseDateTime('12/10/2015 12:00:31' ,'dd/MM/yyyy hh:mm:ss'), parseDateTime('12/10/2016 12:00:31' ,'dd/MM/yyyy hh:mm:ss'), 1);

INSERT INTO Event(id, title, description, EVENT_TYPE, FK_SUBJECT) VALUES (2, 'title', 'desc', 'LECTURE', 1);
INSERT INTO EVENT_DETAILS(starttime, endtime, id) VALUES (parseDateTime('12/11/2015 12:00:31' ,'dd/MM/yyyy hh:mm:ss'), parseDateTime('12/10/2016 12:00:31' ,'dd/MM/yyyy hh:mm:ss'), 2);

INSERT INTO Event(id, title, description, EVENT_TYPE, FK_SUBJECT) VALUES (3, 'title', 'desc', 'LECTURE', 1);
INSERT INTO EVENT_DETAILS(starttime, endtime, id) VALUES (parseDateTime('12/12/2015 12:00:31' ,'dd/MM/yyyy hh:mm:ss'), parseDateTime('12/10/2016 12:00:31' ,'dd/MM/yyyy hh:mm:ss'), 3);
