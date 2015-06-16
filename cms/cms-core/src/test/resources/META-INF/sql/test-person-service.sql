INSERT INTO person(id, email, name, second_name, last_name, personRole) VALUES(1, 'teacher@gmail.com', 'teacherName', 'teacherSecondName', 'teacherLastName', 'TEACHER');
INSERT INTO person(id, email, name, second_name, last_name, personRole) VALUES(2, 'student@gmail.com', 'studentName', 'studentSecondName', 'studentLastName', 'STUDENT');
INSERT INTO courses(id, name, description, startdate, enddate) VALUES (1, 'courseName', 'courseDescription', '2014-01-10', '2015-07-31');
INSERT INTO person_course(course_id, person_id) VALUES (1, 1);