INSERT INTO person(id, email, phone, name, second_name, last_name, personRole) VALUES(1, 'dorogan.olga.test@gmail.com', '123', 'Olga', 'teacherSecondName_1', 'olgaSurname', 'TEACHER');
INSERT INTO person(id, email, phone, name, second_name, last_name, personRole) VALUES(2, 'another@gmail.com', '123', 'teacherName_2', 'teacherSecondName_2', 'teacherSurname_2', 'TEACHER');

INSERT INTO courses(id, name, startdate, enddate, description) VALUES (1, 'Java SE', '2014-01-10', '2015-07-31','Java Platform, Standard Edition (Java SE) offers the rich user interface, performance and security that today''s applications require. With Java SE Training, learn how it gives customers enterprise features that minimize the costs of deployment and maintenance of their Java-based IT environment.');
INSERT INTO courses(id, name, startdate, enddate, description) VALUES (2, 'Java EE', '2014-01-10', '2015-07-31','Companies utilize the new, lightweight Java EE Web Profile to create next-generation web applications. Through Java EE Training, learn more about the full power of the Java EE platform for enterprise applications and more.');

INSERT INTO course_person_status(id, course_id, person_id, course_status) VALUES (1, 1, 1, 'SIGNED');
