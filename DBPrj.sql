drop database CourseManagement;
CREATE DATABASE CourseManagement;
use CourseManagement;

CREATE TABLE Course(
                       id INT AUTO_INCREMENT PRIMARY KEY ,
                       name varchar(255) not null ,
                       price decimal,
                       imgLink varchar(255),
                       classify varchar(255),
                       status TINYINT,
                       createdAt Timestamp DEFAULT CURRENT_TIMESTAMP,
                       updateAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE  CURRENT_TIMESTAMP
);



CREATE TABLE Section (
                         id INT AUTO_INCREMENT PRIMARY KEY,
                         course_id INT,
                         goalTitle VARCHAR(255),
                         contentGoal TEXT,
                         introduce VARCHAR(255),
                         contentIntroduce TEXT,
                         details int,
                         contentDetails TEXT,
                         countLessons varchar(255),
                         durationLesson varchar(255),
                         supportTime Text,
                         classSize varchar(255),
                         contentClassSize Text,
                         FOREIGN KEY (course_id) REFERENCES Course(id)
);

CREATE TABLE Lesson (
                        id INT AUTO_INCREMENT PRIMARY KEY,
                        course_id INT,
                        lesson_number VARCHAR(255) NOT NULL,
                        topics_covered TEXT,
                        outcome TEXT,
                        FOREIGN KEY (course_id) REFERENCES Course(id)
);



CREATE TABLE Instructor (
                            id INT AUTO_INCREMENT PRIMARY KEY,
                            name VARCHAR(255) NOT NULL,
                            gender varchar(255) not null ,
                            bio TEXT, # mô tả về giáo viên
                            email VARCHAR(255) NOT NULL unique ,
                            imageLink varchar(255),
                            phoneNumber VARCHAR(255) unique ,
                            classify varchar(255),
                            createdAt Timestamp DEFAULT CURRENT_TIMESTAMP,
                            updateAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE Account(
                        id INT AUTO_INCREMENT PRIMARY KEY ,
                        userName varchar(255) not null ,
                        password varchar(255) not null,
                        fullName varchar(255) not null,
                        birthday DATE not null ,
                        email varchar(255) not null,
                        phoneNumber varchar(255) not null,
                        imageAccount varchar(255),
                        createdAt Timestamp DEFAULT CURRENT_TIMESTAMP,
                        updateAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                        role enum('admin', 'customer' , "instructor") not null
);

select * from Account where role = 'customer';

-- Bảng lưu trữ thông tin lớp học
CREATE TABLE Class (
                       id INT AUTO_INCREMENT PRIMARY KEY,
                       courseId INT NOT NULL,
                       instructorId INT NOT NULL,
                       location TEXT,
                       startDate DATE,
                       endDate DATE,
                       status TINYINT,
                       createdAt Timestamp DEFAULT CURRENT_TIMESTAMP,
                       updateAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                       FOREIGN KEY (courseId) REFERENCES Course(id),
                       FOREIGN KEY (instructorId) REFERENCES Instructor(id)
);

-- Bảng lưu trữ thông tin học viên đăng ký lớp học
CREATE TABLE Enrollment (
                            id INT AUTO_INCREMENT PRIMARY KEY,
                            classId INT NOT NULL,
                            userId INT NOT NULL,
                            enrollmentDate TIMESTAMP,
                            progress int,
                            status TINYINT,
                            createdAt Timestamp DEFAULT CURRENT_TIMESTAMP,
                            updateAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                            FOREIGN KEY (classId) REFERENCES Class(id),
                            FOREIGN KEY (userId) REFERENCES Account(id)
);


-- Bảng lưu trữ thông tin thông báo
CREATE TABLE Notification (
                              id INT AUTO_INCREMENT PRIMARY KEY,
                              userId INT NOT NULL,
                              message TEXT,
                              dateSent TIMESTAMP,
                              status TINYINT,
                              createdAt Timestamp DEFAULT CURRENT_TIMESTAMP,
                              updateAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                              FOREIGN KEY (userId) REFERENCES Account(id)
);




CREATE TABLE Order_(
                       id INT AUTO_INCREMENT PRIMARY KEY ,
                       userId INT,
                       totalPrice decimal,
                       status TINYINT,
                       createdAt Timestamp DEFAULT CURRENT_TIMESTAMP,
                       updateAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                       FOREIGN KEY (userId) REFERENCES Account(id)
);


CREATE TABLE OrderDetail(
                            id INT PRIMARY KEY AUTO_INCREMENT,
                            courseId INT Not Null ,
                            orderId int NOT NULL ,
                            discount decimal DEFAULT 0,
                            totalAmount decimal,
                            status tinyint,
                            createdAt Timestamp DEFAULT CURRENT_TIMESTAMP,
                            updateAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                            FOREIGN KEY (courseId) REFERENCES Course(id),
                            FOREIGN KEY (orderId) REFERENCES Order_(id)
);


CREATE TABLE Payment (
                         id INT PRIMARY KEY AUTO_INCREMENT,
                         paymentId VARCHAR(255) NOT NULL,
                         userId INT,
                         discount Int,
                         paymentMethod varchar(255),
                         orderDetailId INT NOT NULL,
                         amount DECIMAL,
                         paymentDate TIMESTAMP,
                         status TINYINT,
                         createdAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                         updateAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                         FOREIGN KEY (userId) REFERENCES Account(id),
                         FOREIGN KEY (orderDetailId) REFERENCES OrderDetail(id)
);

Create Table Discount(
	id INT PRIMARY KEY AUTO_INCREMENT,
	code VARCHAR(255) NOT NULL,
	value int default 0,
	createdAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updateAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

INSERT INTO Discount(code,value) 
VALUES
("GIAMGIA10", 10),
("GIAMGIA20", 20),
("GIAMGIA30", 30),
("GIAMGIA40", 40);

-- bảng này lưu trữ lịch học
CREATE TABLE Schedule (
    id INT AUTO_INCREMENT PRIMARY KEY,
    classId INT NOT NULL,
    lessonNumber INT NOT NULL,
    classDate DATE NOT NULL,
    createdAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updateAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (classId) REFERENCES Class(id)
);


-- bảng này để điểm danh
CREATE TABLE Attendance (
    id INT AUTO_INCREMENT PRIMARY KEY,
    enrollmentId INT NOT NULL,
    scheduleId INT NOT NULL,
    attendanceStatus ENUM('present', 'absent') DEFAULT 'absent',
    createdAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updateAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (enrollmentId) REFERENCES Enrollment(id),
    FOREIGN KEY (scheduleId) REFERENCES Schedule(id)
);

SELECT * FROM  Attendance a;

SELECT a.*
FROM Attendance a
INNER JOIN Enrollment e ON a.enrollmentId = e.id
INNER JOIN Schedule s ON a.scheduleId = s.id
INNER JOIN Class c ON e.classId = c.id
WHERE c.id = 3
AND s.classDate = '2024-07-27';


CREATE TABLE SPRING_SESSION (
                                PRIMARY_ID CHAR(36) NOT NULL,
                                SESSION_ID CHAR(36) NOT NULL,
                                CREATION_TIME BIGINT NOT NULL,
                                LAST_ACCESS_TIME BIGINT NOT NULL,
                                MAX_INACTIVE_INTERVAL INT NOT NULL,
                                EXPIRY_TIME BIGINT NOT NULL,
                                PRINCIPAL_NAME VARCHAR(100),
                                CONSTRAINT SPRING_SESSION_PK PRIMARY KEY (PRIMARY_ID)
);

CREATE UNIQUE INDEX SPRING_SESSION_IX1 ON SPRING_SESSION (SESSION_ID);
CREATE INDEX SPRING_SESSION_IX2 ON SPRING_SESSION (EXPIRY_TIME);
CREATE INDEX SPRING_SESSION_IX3 ON SPRING_SESSION (PRINCIPAL_NAME);

CREATE TABLE SPRING_SESSION_ATTRIBUTES (
                                           SESSION_PRIMARY_ID CHAR(36) NOT NULL,
                                           ATTRIBUTE_NAME VARCHAR(200) NOT NULL,
                                           ATTRIBUTE_BYTES BLOB NOT NULL,
                                           CONSTRAINT SPRING_SESSION_ATTRIBUTES_PK PRIMARY KEY (SESSION_PRIMARY_ID, ATTRIBUTE_NAME),
                                           CONSTRAINT SPRING_SESSION_ATTRIBUTES_FK FOREIGN KEY (SESSION_PRIMARY_ID) REFERENCES SPRING_SESSION (PRIMARY_ID) ON DELETE CASCADE
);

CREATE INDEX SPRING_SESSION_ATTRIBUTES_IX1 ON SPRING_SESSION_ATTRIBUTES (SESSION_PRIMARY_ID);

select * From SPRING_SESSION;

CREATE TABLE password_reset_tokens (
                                       id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                       token VARCHAR(255) NOT NULL,
                                       account_id INT NOT NULL,
                                       expiration_date TIMESTAMP NOT NULL,
                                       FOREIGN KEY (account_id) REFERENCES Account(id)
);
INSERT INTO Account(username, password, fullname,imageAccount, birthday, email, phonenumber, role) values ('hongquan', 123456, 'Dang Hong Quan','https://i.imgur.com/1BhEejF.jpeg', '1999-01-01','quandhth2304004@fpt.edu.vn','0383240511', 'customer');
INSERT INTO Account(username, password, fullname,imageAccount ,birthday, email, phonenumber, role) values ('phamhoang', 123456, 'Pham Nhat Hoang','https://i.imgur.com/1BhEejF.jpeg', '1999-01-01','hoangpnth2304021@fpt.edu.vn','0915298826', 'customer');
INSERT INTO Account(username, password, fullname,imageAccount, birthday, email, phonenumber, role) values ('dothao', 123456, 'Do Thi Thao','https://i.imgur.com/1BhEejF.jpeg', '1999-01-01','thaodtth2304010@fpt.edu.vn','0348279942', 'customer');
SELECT * FROM  Account;

-- create admin
INSERT INTO Account(username, password, fullname,imageAccount, birthday, email, phonenumber, role) values ('admin', 123456, 'admin','https://i.imgur.com/1BhEejF.jpeg', '1999-01-01','admin@example.com','00000000000', 'admin');

-- create instructor
INSERT INTO Account(username, password, fullname,imageAccount, birthday, email, phonenumber, role) 
values 
('johnsmith', 123456, 'John Smith','https://i.imgur.com/TIuVvui.png?1', '1999-01-01','johnsmith_ielts@example.com','0123456789', 'instructor'),
('sarahlee', 123456, 'Sarah Lee','https://i.imgur.com/ADQUqCv.png?1', '1999-01-01','sarahlee_toeic@example.com','0911222333', 'instructor'),
('davidtaylor', 123456, 'David Taylor','https://i.imgur.com/TiY9PuH.jpg', '1999-01-01','davidtaylor_toeic@example.com','0933444555', 'instructor'),
('emilyjohnson', 123456, 'Emily Johnson','https://i.imgur.com/tATWZoE.jpg', '1999-01-01','emilyjohnson_ielts@example.com','0987654321', 'instructor'),
('lauramartinez', 123456, 'Laura Martinez','https://i.imgur.com/jQjsgtw.jpg', '1999-01-01','lauramartinez_toeic@example.com','0944555666', 'instructor'),
('michaelbrown', 123456, 'Michael Brown','https://i.imgur.com/yXleNYE.jpg', '1999-01-01','michaelbrown_ielts@example.com','0123987654', 'instructor'),
('sophiadavis', 123456, 'Sophia Davis','https://i.imgur.com/vwx4lTP.jpg', '1999-01-01','sophiadavis_ielts@example.com','0987123456', 'instructor'),
('jameswilson', 123456, 'James Wilson','https://i.imgur.com/us80Xxs.jpg', '1999-01-01','jameswilson_ielts@example.com','0912345678', 'instructor'),
('robertanderson', 123456, 'Robert Anderson','https://i.imgur.com/qLfUpbC.jpg', '1999-01-01','robertanderson_toeic@example.com','0955666777', 'instructor'),
('emmathomas', 123456, 'Emma Thomas','https://i.imgur.com/LuXZpZA.jpg', '1999-01-01','emmathomas_toeic@example.com','0966777888', 'instructor');

INSERT INTO Instructor (name, gender, bio, email, phoneNumber,imageLink,classify)
VALUES
('John Smith', 'Male', 'IELTS instructor with 10 years of teaching and exam preparation experience.', 'johnsmith_ielts@example.com', '0123456789','https://i.imgur.com/TIuVvui.png?1','IELTS'),
('Sarah Lee', 'Female', 'TOEIC instructor with 8 years of experience and effective teaching methods.', 'sarahlee_toeic@example.com', '0911222333','https://i.imgur.com/ADQUqCv.png?1','TOEIC'),
('David Taylor', 'Male', 'TOEIC exam preparation specialist with many years of experience.', 'davidtaylor_toeic@example.com', '0933444555','https://i.imgur.com/TiY9PuH.jpg','TOEIC'),
('Emily Johnson', 'Female', 'IELTS teacher with international certification and many years of experience.', 'emilyjohnson_ielts@example.com', '0987654321','https://i.imgur.com/tATWZoE.jpg','IELTS'),
('Laura Martinez', 'Female', 'TOEIC teacher with high-quality lessons and students achieving high scores.', 'lauramartinez_toeic@example.com', '0944555666','https://i.imgur.com/jQjsgtw.jpg','TOEIC'),
('Michael Brown', 'Male', 'IELTS specialist with modern teaching methods and extensive experience.', 'michaelbrown_ielts@example.com', '0123987654','https://i.imgur.com/yXleNYE.jpg','IELTS'),
('Sophia Davis', 'Female', 'Master of Linguistics, specializing in IELTS exam preparation.', 'sophiadavis_ielts@example.com', '0987123456','https://i.imgur.com/vwx4lTP.jpg','IELTS'),
('James Wilson', 'Male', 'IELTS instructor with many students achieving high scores.', 'jameswilson_ielts@example.com', '0912345678','https://i.imgur.com/us80Xxs.jpg','IELTS'),
('Robert Anderson', 'Male', 'Master of Linguistics, specializing in TOEIC exam preparation.', 'robertanderson_toeic@example.com', '0955666777','https://i.imgur.com/qLfUpbC.jpg','TOEIC'),
('Emma Thomas', 'Female', 'TOEIC instructor with many years of experience and advanced teaching methods.', 'emmathomas_toeic@example.com', '0966777888','https://i.imgur.com/LuXZpZA.jpg','TOEIC');


SELECT * FROM Instructor;


-- course TOEIC 2 Skills
INSERT INTO Course(name, price,imgLink,classify, status) values ('Toeic Basic', 50,'https://i.imgur.com/8nitwKO.png','TOEIC2', 0);
INSERT INTO Course(name, price,imgLink,classify, status) values ('Toeic Pre', 100,'https://i.imgur.com/k16v1W1.png','TOEIC2', 0);
INSERT INTO Course(name, price,imgLink,classify, status) values ('Toeic A', 120,'https://i.imgur.com/5OziSxj.png','TOEIC2', 0);
INSERT INTO Course(name, price,imgLink,classify, status) values ('Toeic B', 150,'https://i.imgur.com/QEeFSXO.png','TOEIC2', 0);
INSERT INTO Course(name, price,imgLink,classify, status) values ('Toeic Practice',80,'https://i.imgur.com/N0IJ9R4.png','TOEIC2', 0);

-- course TOEIC 4 skills
INSERT INTO Course(name, price,imgLink,classify, status) values ('Toeic Basic (4 Skill)', 150,'https://i.imgur.com/8nitwKO.png','TOEIC4', 0);
INSERT INTO Course(name, price,imgLink,classify, status) values ('Toeic Pre (4 Skill)', 120,'https://i.imgur.com/k16v1W1.png','TOEIC4', 0);
INSERT INTO Course(name, price,imgLink,classify, status) values ('Toeic A (4 Skill)', 100,'https://i.imgur.com/5OziSxj.png','TOEIC4', 0);
INSERT INTO Course(name, price,imgLink,classify, status) values ('Toeic A+', 120,'https://i.imgur.com/xKUk4uC.png','TOEIC4', 0);
INSERT INTO Course(name, price,imgLink,classify, status) values ('Toeic B (4 Skill)',100,'https://i.imgur.com/QEeFSXO.png','TOEIC4', 0);
INSERT INTO Course(name, price,imgLink,classify, status) values ('Toeic B+', 120,'https://i.imgur.com/sW2wxFM.png','TOEIC4', 0);
INSERT INTO Course(name, price,imgLink,classify, status) values ('Toeic Training', 200,'https://i.imgur.com/N0IJ9R4.png','TOEIC4', 0);

-- course IELTS
INSERT INTO Course(name, price,imgLink,classify, status) values ('Ielts Basic', 50,'https://i.imgur.com/SG7cvon.png','IELTS', 0);
INSERT INTO Course(name, price,imgLink,classify, status) values ('Ielts Pre', 100,'https://i.imgur.com/VYSM6rM.png','IELTS', 0);
INSERT INTO Course(name, price,imgLink,classify, status) values ('Ielts Pre Foundation', 120,'https://i.imgur.com/YEfAhkp.png','IELTS', 0);
INSERT INTO Course(name, price,imgLink,classify, status) values ('Ielts  Foundation', 150,'https://i.imgur.com/UEmY8an.png','IELTS', 0);
INSERT INTO Course(name, price,imgLink,classify, status) values ('Ielts Speaking/Writing A', 170,'https://i.imgur.com/dSWJbYG.png','IELTS', 0);
INSERT INTO Course(name, price,imgLink,classify, status) values ('Ielts  A', 150,'https://i.imgur.com/I5R0sYs.png','IELTS', 0);
INSERT INTO Course(name, price,imgLink,classify, status) values ('Ielts Speaking/Writing B', 120,'https://i.imgur.com/22AovQJ.png','IELTS', 0);
INSERT INTO Course(name, price,imgLink,classify, status) values ('Ielts B', 130,'https://i.imgur.com/xHHgR77.png','IELTS', 0);
INSERT INTO Course(name, price,imgLink,classify, status) values ('Ielts Total review', 160,'https://i.imgur.com/O44JfSB.png','IELTS', 0);


SELECT * FROM Course;

INSERT INTO Order_(userId, status) values(1,0);
INSERT INTO Order_(userId, status) values(2,0);
INSERT INTO Order_(userId, status) values(3,0);

SELECT * FROM Order_;

INSERT INTO Class(courseId, instructorId, location, startDate, endDate, status) values (1,1,'14h30-16h', '2024-08-06','2024-09-19', 0);
INSERT INTO Class(courseId, instructorId, location, startDate, endDate, status) values (1,2,'14h30-16h', '2024-08-05','2024-09-18', 0);
INSERT INTO Class(courseId, instructorId, location, startDate, endDate, status) values (2,3,'14h30-16h', '2024-07-18','2024-08-31', 0);
INSERT INTO Class(courseId, instructorId, location, startDate, endDate, status) values (1,1,'17h-18h30', '2024-08-06','2024-09-19', 0);
INSERT INTO Class(courseId, instructorId, location, startDate, endDate, status) values (3,2,'17h-18h30', '2024-08-06','2024-09-19', 0);
INSERT INTO Class(courseId, instructorId, location, startDate, endDate, status) values (1,2,'9h-10h30', '2024-07-30','2024-09-12', 0);
INSERT INTO Class(courseId, instructorId, location, startDate, endDate, status) values (2,3,'17h-18h30', '2024-08-07','2024-09-20', 0);
INSERT INTO Class(courseId, instructorId, location, startDate, endDate, status) values (3,3,'17h-18h30', '2024-06-16','2024-07-31', 0);

select * FROM Class;


-- thêm dữ liệu bảng schedule
-- Lớp học với ID = 1 bắt đầu từ '2024-08-06', học vào thứ 3, 5, 7
INSERT INTO Schedule (classId, lessonNumber, classDate) VALUES (1, 1, '2024-08-06');
INSERT INTO Schedule (classId, lessonNumber, classDate) VALUES (1, 2, '2024-08-08');
INSERT INTO Schedule (classId, lessonNumber, classDate) VALUES (1, 3, '2024-08-10');
INSERT INTO Schedule (classId, lessonNumber, classDate) VALUES (1, 4, '2024-08-13');
INSERT INTO Schedule (classId, lessonNumber, classDate) VALUES (1, 5, '2024-08-15');
INSERT INTO Schedule (classId, lessonNumber, classDate) VALUES (1, 6, '2024-08-17');
INSERT INTO Schedule (classId, lessonNumber, classDate) VALUES (1, 7, '2024-08-20');
INSERT INTO Schedule (classId, lessonNumber, classDate) VALUES (1, 8, '2024-08-22');
INSERT INTO Schedule (classId, lessonNumber, classDate) VALUES (1, 9, '2024-08-24');
INSERT INTO Schedule (classId, lessonNumber, classDate) VALUES (1, 10, '2024-08-27');
INSERT INTO Schedule (classId, lessonNumber, classDate) VALUES (1, 11, '2024-08-29');
INSERT INTO Schedule (classId, lessonNumber, classDate) VALUES (1, 12, '2024-08-31');
INSERT INTO Schedule (classId, lessonNumber, classDate) VALUES (1, 13, '2024-09-03');
INSERT INTO Schedule (classId, lessonNumber, classDate) VALUES (1, 14, '2024-09-05');
INSERT INTO Schedule (classId, lessonNumber, classDate) VALUES (1, 15, '2024-09-07');
INSERT INTO Schedule (classId, lessonNumber, classDate) VALUES (1, 16, '2024-09-10');
INSERT INTO Schedule (classId, lessonNumber, classDate) VALUES (1, 17, '2024-09-12');
INSERT INTO Schedule (classId, lessonNumber, classDate) VALUES (1, 18, '2024-09-14');
INSERT INTO Schedule (classId, lessonNumber, classDate) VALUES (1, 19, '2024-09-17');
INSERT INTO Schedule (classId, lessonNumber, classDate) VALUES (1, 20, '2024-09-19');

-- Lớp học với ID = 2 bắt đầu từ '2024-08-05', học vào thứ 2, 4, 6
INSERT INTO Schedule (classId, lessonNumber, classDate) VALUES (2, 1, '2024-08-05');
INSERT INTO Schedule (classId, lessonNumber, classDate) VALUES (2, 2, '2024-08-07');
INSERT INTO Schedule (classId, lessonNumber, classDate) VALUES (2, 3, '2024-08-09');
INSERT INTO Schedule (classId, lessonNumber, classDate) VALUES (2, 4, '2024-08-12');
INSERT INTO Schedule (classId, lessonNumber, classDate) VALUES (2, 5, '2024-08-14');
INSERT INTO Schedule (classId, lessonNumber, classDate) VALUES (2, 6, '2024-08-16');
INSERT INTO Schedule (classId, lessonNumber, classDate) VALUES (2, 7, '2024-08-19');
INSERT INTO Schedule (classId, lessonNumber, classDate) VALUES (2, 8, '2024-08-21');
INSERT INTO Schedule (classId, lessonNumber, classDate) VALUES (2, 9, '2024-08-23');
INSERT INTO Schedule (classId, lessonNumber, classDate) VALUES (2, 10, '2024-08-26');
INSERT INTO Schedule (classId, lessonNumber, classDate) VALUES (2, 11, '2024-08-28');
INSERT INTO Schedule (classId, lessonNumber, classDate) VALUES (2, 12, '2024-08-30');
INSERT INTO Schedule (classId, lessonNumber, classDate) VALUES (2, 13, '2024-09-02');
INSERT INTO Schedule (classId, lessonNumber, classDate) VALUES (2, 14, '2024-09-04');
INSERT INTO Schedule (classId, lessonNumber, classDate) VALUES (2, 15, '2024-09-06');
INSERT INTO Schedule (classId, lessonNumber, classDate) VALUES (2, 16, '2024-09-09');
INSERT INTO Schedule (classId, lessonNumber, classDate) VALUES (2, 17, '2024-09-11');
INSERT INTO Schedule (classId, lessonNumber, classDate) VALUES (2, 18, '2024-09-13');
INSERT INTO Schedule (classId, lessonNumber, classDate) VALUES (2, 19, '2024-09-16');
INSERT INTO Schedule (classId, lessonNumber, classDate) VALUES (2, 20, '2024-09-18');

-- Lớp học với ID = 3 bắt đầu từ '2024-08-08', học vào thứ 3, 5, 7
INSERT INTO Schedule (classId, lessonNumber, classDate) VALUES (3, 1, '2024-07-18'); -- Thứ 5
INSERT INTO Schedule (classId, lessonNumber, classDate) VALUES (3, 2, '2024-07-20'); -- Thứ 7
INSERT INTO Schedule (classId, lessonNumber, classDate) VALUES (3, 3, '2024-07-23'); -- Thứ 3
INSERT INTO Schedule (classId, lessonNumber, classDate) VALUES (3, 4, '2024-07-25'); -- Thứ 5
INSERT INTO Schedule (classId, lessonNumber, classDate) VALUES (3, 5, '2024-07-27'); -- Thứ 7
INSERT INTO Schedule (classId, lessonNumber, classDate) VALUES (3, 6, '2024-07-30'); -- Thứ 3
INSERT INTO Schedule (classId, lessonNumber, classDate) VALUES (3, 7, '2024-08-01'); -- Thứ 5
INSERT INTO Schedule (classId, lessonNumber, classDate) VALUES (3, 8, '2024-08-03'); -- Thứ 7
INSERT INTO Schedule (classId, lessonNumber, classDate) VALUES (3, 9, '2024-08-06'); -- Thứ 3
INSERT INTO Schedule (classId, lessonNumber, classDate) VALUES (3, 10, '2024-08-08'); -- Thứ 5
INSERT INTO Schedule (classId, lessonNumber, classDate) VALUES (3, 11, '2024-08-10'); -- Thứ 7
INSERT INTO Schedule (classId, lessonNumber, classDate) VALUES (3, 12, '2024-08-13'); -- Thứ 3
INSERT INTO Schedule (classId, lessonNumber, classDate) VALUES (3, 13, '2024-08-15'); -- Thứ 5
INSERT INTO Schedule (classId, lessonNumber, classDate) VALUES (3, 14, '2024-08-17'); -- Thứ 7
INSERT INTO Schedule (classId, lessonNumber, classDate) VALUES (3, 15, '2024-08-20'); -- Thứ 3
INSERT INTO Schedule (classId, lessonNumber, classDate) VALUES (3, 16, '2024-08-22'); -- Thứ 5
INSERT INTO Schedule (classId, lessonNumber, classDate) VALUES (3, 17, '2024-08-24'); -- Thứ 7
INSERT INTO Schedule (classId, lessonNumber, classDate) VALUES (3, 18, '2024-08-27'); -- Thứ 3
INSERT INTO Schedule (classId, lessonNumber, classDate) VALUES (3, 19, '2024-08-29'); -- Thứ 5
INSERT INTO Schedule (classId, lessonNumber, classDate) VALUES (3, 20, '2024-08-31'); -- Thứ 7

-- Lớp học với ID = 4 bắt đầu từ '2024-08-06', học vào thứ 3, 5, 7
INSERT INTO Schedule (classId, lessonNumber, classDate) VALUES (4, 1, '2024-08-06');
INSERT INTO Schedule (classId, lessonNumber, classDate) VALUES (4, 2, '2024-08-08');
INSERT INTO Schedule (classId, lessonNumber, classDate) VALUES (4, 3, '2024-08-10');
INSERT INTO Schedule (classId, lessonNumber, classDate) VALUES (4, 4, '2024-08-13');
INSERT INTO Schedule (classId, lessonNumber, classDate) VALUES (4, 5, '2024-08-15');
INSERT INTO Schedule (classId, lessonNumber, classDate) VALUES (4, 6, '2024-08-17');
INSERT INTO Schedule (classId, lessonNumber, classDate) VALUES (4, 7, '2024-08-20');
INSERT INTO Schedule (classId, lessonNumber, classDate) VALUES (4, 8, '2024-08-22');
INSERT INTO Schedule (classId, lessonNumber, classDate) VALUES (4, 9, '2024-08-24');
INSERT INTO Schedule (classId, lessonNumber, classDate) VALUES (4, 10, '2024-08-27');
INSERT INTO Schedule (classId, lessonNumber, classDate) VALUES (4, 11, '2024-08-29');
INSERT INTO Schedule (classId, lessonNumber, classDate) VALUES (4, 12, '2024-08-31');
INSERT INTO Schedule (classId, lessonNumber, classDate) VALUES (4, 13, '2024-09-03');
INSERT INTO Schedule (classId, lessonNumber, classDate) VALUES (4, 14, '2024-09-05');
INSERT INTO Schedule (classId, lessonNumber, classDate) VALUES (4, 15, '2024-09-07');
INSERT INTO Schedule (classId, lessonNumber, classDate) VALUES (4, 16, '2024-09-10');
INSERT INTO Schedule (classId, lessonNumber, classDate) VALUES (4, 17, '2024-09-12');
INSERT INTO Schedule (classId, lessonNumber, classDate) VALUES (4, 18, '2024-09-14');
INSERT INTO Schedule (classId, lessonNumber, classDate) VALUES (4, 19, '2024-09-17');
INSERT INTO Schedule (classId, lessonNumber, classDate) VALUES (4, 20, '2024-09-19');

-- Lớp học với ID = 5 bắt đầu từ '2024-08-06', học vào thứ 3, 5, 7
INSERT INTO Schedule (classId, lessonNumber, classDate) VALUES (5, 1, '2024-08-06');
INSERT INTO Schedule (classId, lessonNumber, classDate) VALUES (5, 2, '2024-08-08');
INSERT INTO Schedule (classId, lessonNumber, classDate) VALUES (5, 3, '2024-08-10');
INSERT INTO Schedule (classId, lessonNumber, classDate) VALUES (5, 4, '2024-08-13');
INSERT INTO Schedule (classId, lessonNumber, classDate) VALUES (5, 5, '2024-08-15');
INSERT INTO Schedule (classId, lessonNumber, classDate) VALUES (5, 6, '2024-08-17');
INSERT INTO Schedule (classId, lessonNumber, classDate) VALUES (5, 7, '2024-08-20');
INSERT INTO Schedule (classId, lessonNumber, classDate) VALUES (5, 8, '2024-08-22');
INSERT INTO Schedule (classId, lessonNumber, classDate) VALUES (5, 9, '2024-08-24');
INSERT INTO Schedule (classId, lessonNumber, classDate) VALUES (5, 10, '2024-08-27');
INSERT INTO Schedule (classId, lessonNumber, classDate) VALUES (5, 11, '2024-08-29');
INSERT INTO Schedule (classId, lessonNumber, classDate) VALUES (5, 12, '2024-08-31');
INSERT INTO Schedule (classId, lessonNumber, classDate) VALUES (5, 13, '2024-09-03');
INSERT INTO Schedule (classId, lessonNumber, classDate) VALUES (5, 14, '2024-09-05');
INSERT INTO Schedule (classId, lessonNumber, classDate) VALUES (5, 15, '2024-09-07');
INSERT INTO Schedule (classId, lessonNumber, classDate) VALUES (5, 16, '2024-09-10');
INSERT INTO Schedule (classId, lessonNumber, classDate) VALUES (5, 17, '2024-09-12');
INSERT INTO Schedule (classId, lessonNumber, classDate) VALUES (5, 18, '2024-09-14');
INSERT INTO Schedule (classId, lessonNumber, classDate) VALUES (5, 19, '2024-09-17');
INSERT INTO Schedule (classId, lessonNumber, classDate) VALUES (5, 20, '2024-09-19');

-- Lớp học với ID = 6 bắt đầu từ '2024-07-30', học vào thứ 3, 5, 7
INSERT INTO Schedule (classId, lessonNumber, classDate) VALUES (6, 1, '2024-07-30');
INSERT INTO Schedule (classId, lessonNumber, classDate) VALUES (6, 2, '2024-08-01');
INSERT INTO Schedule (classId, lessonNumber, classDate) VALUES (6, 3, '2024-08-03');
INSERT INTO Schedule (classId, lessonNumber, classDate) VALUES (6, 4, '2024-08-06');
INSERT INTO Schedule (classId, lessonNumber, classDate) VALUES (6, 5, '2024-08-08');
INSERT INTO Schedule (classId, lessonNumber, classDate) VALUES (6, 6, '2024-08-10');
INSERT INTO Schedule (classId, lessonNumber, classDate) VALUES (6, 7, '2024-08-13');
INSERT INTO Schedule (classId, lessonNumber, classDate) VALUES (6, 8, '2024-08-15');
INSERT INTO Schedule (classId, lessonNumber, classDate) VALUES (6, 9, '2024-08-17');
INSERT INTO Schedule (classId, lessonNumber, classDate) VALUES (6, 10, '2024-08-20');
INSERT INTO Schedule (classId, lessonNumber, classDate) VALUES (6, 11, '2024-08-22');
INSERT INTO Schedule (classId, lessonNumber, classDate) VALUES (6, 12, '2024-08-24');
INSERT INTO Schedule (classId, lessonNumber, classDate) VALUES (6, 13, '2024-08-27');
INSERT INTO Schedule (classId, lessonNumber, classDate) VALUES (6, 14, '2024-08-29');
INSERT INTO Schedule (classId, lessonNumber, classDate) VALUES (6, 15, '2024-08-31');
INSERT INTO Schedule (classId, lessonNumber, classDate) VALUES (6, 16, '2024-09-03');
INSERT INTO Schedule (classId, lessonNumber, classDate) VALUES (6, 17, '2024-09-05');
INSERT INTO Schedule (classId, lessonNumber, classDate) VALUES (6, 18, '2024-09-07');
INSERT INTO Schedule (classId, lessonNumber, classDate) VALUES (6, 19, '2024-09-10');
INSERT INTO Schedule (classId, lessonNumber, classDate) VALUES (6, 20, '2024-09-12');

-- Lớp học với ID = 7 bắt đầu từ '2024-08-07', học vào thứ 2, 4, 6
INSERT INTO Schedule (classId, lessonNumber, classDate) VALUES (7, 1, '2024-08-07');
INSERT INTO Schedule (classId, lessonNumber, classDate) VALUES (7, 2, '2024-08-09');
INSERT INTO Schedule (classId, lessonNumber, classDate) VALUES (7, 3, '2024-08-12');
INSERT INTO Schedule (classId, lessonNumber, classDate) VALUES (7, 4, '2024-08-14');
INSERT INTO Schedule (classId, lessonNumber, classDate) VALUES (7, 5, '2024-08-16');
INSERT INTO Schedule (classId, lessonNumber, classDate) VALUES (7, 6, '2024-08-19');
INSERT INTO Schedule (classId, lessonNumber, classDate) VALUES (7, 7, '2024-08-21');
INSERT INTO Schedule (classId, lessonNumber, classDate) VALUES (7, 8, '2024-08-23');
INSERT INTO Schedule (classId, lessonNumber, classDate) VALUES (7, 9, '2024-08-26');
INSERT INTO Schedule (classId, lessonNumber, classDate) VALUES (7, 10, '2024-08-28');
INSERT INTO Schedule (classId, lessonNumber, classDate) VALUES (7, 11, '2024-08-30');
INSERT INTO Schedule (classId, lessonNumber, classDate) VALUES (7, 12, '2024-09-02');
INSERT INTO Schedule (classId, lessonNumber, classDate) VALUES (7, 13, '2024-09-04');
INSERT INTO Schedule (classId, lessonNumber, classDate) VALUES (7, 14, '2024-09-06');
INSERT INTO Schedule (classId, lessonNumber, classDate) VALUES (7, 15, '2024-09-09');
INSERT INTO Schedule (classId, lessonNumber, classDate) VALUES (7, 16, '2024-09-11');
INSERT INTO Schedule (classId, lessonNumber, classDate) VALUES (7, 17, '2024-09-13');
INSERT INTO Schedule (classId, lessonNumber, classDate) VALUES (7, 18, '2024-09-16');
INSERT INTO Schedule (classId, lessonNumber, classDate) VALUES (7, 19, '2024-09-18');
INSERT INTO Schedule (classId, lessonNumber, classDate) VALUES (7, 20, '2024-09-20');

-- Lớp học với ID = 8 bắt đầu từ '2024-06-16', học vào thứ 2, 4, 6
INSERT INTO Schedule (classId, lessonNumber, classDate) VALUES (8, 1, '2024-06-17'); -- Thứ 2
INSERT INTO Schedule (classId, lessonNumber, classDate) VALUES (8, 2, '2024-06-19'); -- Thứ 4
INSERT INTO Schedule (classId, lessonNumber, classDate) VALUES (8, 3, '2024-06-21'); -- Thứ 6
INSERT INTO Schedule (classId, lessonNumber, classDate) VALUES (8, 4, '2024-06-24'); -- Thứ 2
INSERT INTO Schedule (classId, lessonNumber, classDate) VALUES (8, 5, '2024-06-26'); -- Thứ 4
INSERT INTO Schedule (classId, lessonNumber, classDate) VALUES (8, 6, '2024-06-28'); -- Thứ 6
INSERT INTO Schedule (classId, lessonNumber, classDate) VALUES (8, 7, '2024-07-01'); -- Thứ 2
INSERT INTO Schedule (classId, lessonNumber, classDate) VALUES (8, 8, '2024-07-03'); -- Thứ 4
INSERT INTO Schedule (classId, lessonNumber, classDate) VALUES (8, 9, '2024-07-05'); -- Thứ 6
INSERT INTO Schedule (classId, lessonNumber, classDate) VALUES (8, 10, '2024-07-08'); -- Thứ 2
INSERT INTO Schedule (classId, lessonNumber, classDate) VALUES (8, 11, '2024-07-10'); -- Thứ 4
INSERT INTO Schedule (classId, lessonNumber, classDate) VALUES (8, 12, '2024-07-12'); -- Thứ 6
INSERT INTO Schedule (classId, lessonNumber, classDate) VALUES (8, 13, '2024-07-15'); -- Thứ 2
INSERT INTO Schedule (classId, lessonNumber, classDate) VALUES (8, 14, '2024-07-17'); -- Thứ 4
INSERT INTO Schedule (classId, lessonNumber, classDate) VALUES (8, 15, '2024-07-19'); -- Thứ 6
INSERT INTO Schedule (classId, lessonNumber, classDate) VALUES (8, 16, '2024-07-22'); -- Thứ 2
INSERT INTO Schedule (classId, lessonNumber, classDate) VALUES (8, 17, '2024-07-24'); -- Thứ 4
INSERT INTO Schedule (classId, lessonNumber, classDate) VALUES (8, 18, '2024-07-26'); -- Thứ 6
INSERT INTO Schedule (classId, lessonNumber, classDate) VALUES (8, 19, '2024-07-29'); -- Thứ 2
INSERT INTO Schedule (classId, lessonNumber, classDate) VALUES (8, 20, '2024-07-31'); -- Thứ 4

SELECT  * FROM Payment ;

-- TOEIC 2 Skills:Basic
INSERT INTO Section(course_id, goalTitle, contentGoal, introduce, contentIntroduce, details, contentDetails, countLessons, durationLesson, supportTime, classSize, contentClassSize)
values (1,
        'Goal: 250+ TOEIC Basic',
        'Students will obtain a TOEIC certificate at 110 within 2 months after the course ends (to ensure sufficient knowledge retention for the exam, a free 100% retake is offered if the result is below 550).\n Additionally, students must meet the attendance and assignment requirements as per the class regulations.',
        'This course is for those who have a foundation but have never studied TOEIC or have studied but scored below 600.',
        'The course will focus on essential TOEIC tasks, covering all listening sections (part 1-2-3-4), while simultaneously reviewing basic grammar and introducing fundamental reading sections. With over 15 vocabulary topics through FLASHCARDS, students will quickly expand their vocabulary. After the course, students typically achieve a score of 550+. This vocabulary set is compiled from ETS TOEIC exam materials to facilitate learning.',
        27,
        'Complete all listening sections (part 1-2-3-4)\nReview basic grammar\nFamiliarize with basic reading sections',
        '3 lessons per week','1.5 hours per lesson',
        'Shift 1: 18:00-18:30, Shift 2: 20:00-21:30 on evenings of Monday, Wednesday, Friday or Tuesday, Thursday, Saturday','23-25 students','The classroom is equipped with air conditioning and a large screen TV.');
SELECT * FROM `Section` s ;
-- Lesson 1 - Lesson 3
INSERT INTO Lesson (course_id, lesson_number, topics_covered, outcome)
VALUES (1, 'Lesson 1 - Lesson 3', 'Practice part 1\nPeople description pictures\nObject description pictures\nMixed pictures', 'Ability to describe situations related to people, including appearance, emotional states, and daily activities.\nCapability to describe and identify common objects through images, such as utensils, everyday items, and more.');

-- Lesson 4 - Lesson 8
INSERT INTO Lesson (course_id, lesson_number, topics_covered, outcome)
VALUES (1, 'Lesson 4 - Lesson 8', 'Practice Part 2', 'Learn about everyday communication situations, including discussing work and daily life. Expand vocabulary related to topics such as travel, shopping, and entertainment. Practice listening skills and understanding content from simple and useful dialogues.');

-- Lesson 9 - Lesson 19
INSERT INTO Lesson (course_id, lesson_number, topics_covered, outcome)
VALUES (1, 'Lesson 9 - Lesson 19', 'Practice Part 3\nGrammar Part 5-6', 'Study basic grammar structures such as present simple, past simple, and conditional sentences. Apply grammar rules to enhance accuracy and confidence in speaking and writing English.');

-- Lesson 20 - Lesson 26
INSERT INTO Lesson (course_id, lesson_number, topics_covered, outcome)
VALUES (1, 'Lesson 20 - Lesson 26', 'Practice part 4\nGrammar Part 5-6\nPractice tests', 'Develop the ability to synthesize and apply learned knowledge in real-life situations. Familiarize yourself with high-applicability exercises, such as reading and evaluating information from short passages and articles.');

-- Lesson 27
INSERT INTO Lesson (course_id, lesson_number, topics_covered, outcome)
VALUES (1, 'Lesson 27', 'Final test', 'Comprehensive assessment of listening, reading, speaking, and writing skills in the TOEIC exam format. Evaluate personal progress and determine readiness for the actual exam.');


-- TOEIC 2 skills: TOEIC PRE
-- Course description section TOEIC pre
INSERT INTO Section(course_id, goalTitle, contentGoal, introduce, contentIntroduce, details, contentDetails, countLessons, durationLesson, supportTime, classSize, contentClassSize)
values (2,
        'Goal: 350+ TOEIC PRE',
        'Students will obtain a TOEIC certificate at 110 within 2 months after the course ends (to ensure sufficient knowledge retention for the exam, a free 100% retake is offered if the result is below 550).\n Additionally, students must meet the attendance and assignment requirements as per the class regulations.',
        'This course is for those who have a foundation but have never studied TOEIC or have studied but scored below 600.',
        'The course will focus on essential TOEIC tasks, covering all listening sections (part 1-2-3-4), while simultaneously reviewing basic grammar and introducing fundamental reading sections. With over 15 vocabulary topics through FLASHCARDS, students will quickly expand their vocabulary. After the course, students typically achieve a score of 550+. This vocabulary set is compiled from ETS TOEIC exam materials to facilitate learning.',
        27,
        'Complete all listening sections (part 1-2-3-4)\nReview basic grammar\nFamiliarize with basic reading sections',
        '3 lessons per week','1.5 hours per lesson',
        'Shift 1: 18:00-18:30, Shift 2: 20:00-21:30 on evenings of Monday, Wednesday, Friday or Tuesday, Thursday, Saturday','23-25 students','The classroom is equipped with air conditioning and a large screen TV.');
SELECT * FROM `Section` s ;
-- Lesson 1 - Lesson 3
INSERT INTO Lesson (course_id, lesson_number, topics_covered, outcome)
VALUES (2, 'Lesson 1 - Lesson 3', 'Practice part 1\nPeople description pictures\nObject description pictures\nMixed pictures', 'Ability to describe situations related to people, including appearance, emotional states, and daily activities.\nCapability to describe and identify common objects through images, such as utensils, everyday items, and more.');

-- Lesson 4 - Lesson 8
INSERT INTO Lesson (course_id, lesson_number, topics_covered, outcome)
VALUES (2, 'Lesson 4 - Lesson 8', 'Practice Part 2', 'Learn about everyday communication situations, including discussing work and daily life. Expand vocabulary related to topics such as travel, shopping, and entertainment. Practice listening skills and understanding content from simple and useful dialogues.');

-- Lesson 9 - Lesson 19
INSERT INTO Lesson (course_id, lesson_number, topics_covered, outcome)
VALUES (2, 'Lesson 9 - Lesson 19', 'Practice Part 3\nGrammar Part 5-6', 'Study basic grammar structures such as present simple, past simple, and conditional sentences. Apply grammar rules to enhance accuracy and confidence in speaking and writing English.');

-- Lesson 20 - Lesson 26
INSERT INTO Lesson (course_id, lesson_number, topics_covered, outcome)
VALUES (2, 'Lesson 20 - Lesson 26', 'Practice part 4\nGrammar Part 5-6\nPractice tests', 'Develop the ability to synthesize and apply learned knowledge in real-life situations. Familiarize yourself with high-applicability exercises, such as reading and evaluating information from short passages and articles.');

-- Lesson 27
INSERT INTO Lesson (course_id, lesson_number, topics_covered, outcome)
VALUES (2, 'Lesson 27', 'Final test', 'Comprehensive assessment of listening, reading, speaking, and writing skills in the TOEIC exam format. Evaluate personal progress and determine readiness for the actual exam.');



-- TOEIC A 2skills: TOEIC A
INSERT INTO Section(course_id, goalTitle, contentGoal, introduce, contentIntroduce, details, contentDetails, countLessons, durationLesson, supportTime, classSize, contentClassSize)
values (3,
        'Goal: 500+ TOEIC A',
        'Students will obtain a TOEIC certificate at 110 within 2 months after the course ends (to ensure sufficient knowledge retention for the exam, a free 100% retake is offered if the result is below 550).\n Additionally, students must meet the attendance and assignment requirements as per the class regulations.',
        'This course is for those who have a foundation but have never studied TOEIC or have studied but scored below 600.',
        'The course will focus on essential TOEIC tasks, covering all listening sections (part 1-2-3-4), while simultaneously reviewing basic grammar and introducing fundamental reading sections. With over 15 vocabulary topics through FLASHCARDS, students will quickly expand their vocabulary. After the course, students typically achieve a score of 550+. This vocabulary set is compiled from ETS TOEIC exam materials to facilitate learning.',
        27,
        'Complete all listening sections (part 1-2-3-4)\nReview basic grammar\nFamiliarize with basic reading sections',
        '3 lessons per week','1.5 hours per lesson',
        'Shift 1: 18:00-18:30, Shift 2: 20:00-21:30 on evenings of Monday, Wednesday, Friday or Tuesday, Thursday, Saturday','23-25 students','The classroom is equipped with air conditioning and a large screen TV.');
SELECT * FROM `Section` s ;
-- Lesson 1 - Lesson 3
INSERT INTO Lesson (course_id, lesson_number, topics_covered, outcome)
VALUES (3, 'Lesson 1 - Lesson 3', 'Practice part 1\nPeople description pictures\nObject description pictures\nMixed pictures', 'Ability to describe situations related to people, including appearance, emotional states, and daily activities.\nCapability to describe and identify common objects through images, such as utensils, everyday items, and more.');

-- Lesson 4 - Lesson 8
INSERT INTO Lesson (course_id, lesson_number, topics_covered, outcome)
VALUES (3, 'Lesson 4 - Lesson 8', 'Practice Part 2', 'Learn about everyday communication situations, including discussing work and daily life. Expand vocabulary related to topics such as travel, shopping, and entertainment. Practice listening skills and understanding content from simple and useful dialogues.');

-- Lesson 9 - Lesson 19
INSERT INTO Lesson (course_id, lesson_number, topics_covered, outcome)
VALUES (3, 'Lesson 9 - Lesson 19', 'Practice Part 3\nGrammar Part 5-6', 'Study basic grammar structures such as present simple, past simple, and conditional sentences. Apply grammar rules to enhance accuracy and confidence in speaking and writing English.');

-- Lesson 20 - Lesson 26
INSERT INTO Lesson (course_id, lesson_number, topics_covered, outcome)
VALUES (3, 'Lesson 20 - Lesson 26', 'Practice part 4\nGrammar Part 5-6\nPractice tests', 'Develop the ability to synthesize and apply learned knowledge in real-life situations. Familiarize yourself with high-applicability exercises, such as reading and evaluating information from short passages and articles.');

-- Lesson 27
INSERT INTO Lesson (course_id, lesson_number, topics_covered, outcome)
VALUES (3, 'Lesson 27', 'Final test', 'Comprehensive assessment of listening, reading, speaking, and writing skills in the TOEIC exam format. Evaluate personal progress and determine readiness for the actual exam.');

-- TOECIC B 2 skills
INSERT INTO Section(course_id, goalTitle, contentGoal, introduce, contentIntroduce, details, contentDetails, countLessons, durationLesson, supportTime, classSize, contentClassSize)
values (4,
        'Goal: 650+ TOEIC B',
        'Students will obtain a TOEIC certificate at 110 within 2 months after the course ends (to ensure sufficient knowledge retention for the exam, a free 100% retake is offered if the result is below 550).\n Additionally, students must meet the attendance and assignment requirements as per the class regulations.',
        'This course is for those who have a foundation but have never studied TOEIC or have studied but scored below 600.',
        'The course will focus on essential TOEIC tasks, covering all listening sections (part 1-2-3-4), while simultaneously reviewing basic grammar and introducing fundamental reading sections. With over 15 vocabulary topics through FLASHCARDS, students will quickly expand their vocabulary. After the course, students typically achieve a score of 550+. This vocabulary set is compiled from ETS TOEIC exam materials to facilitate learning.',
        27,
        'Complete all listening sections (part 1-2-3-4)\nReview basic grammar\nFamiliarize with basic reading sections',
        '3 lessons per week','1.5 hours per lesson',
        'Shift 1: 18:00-18:30, Shift 2: 20:00-21:30 on evenings of Monday, Wednesday, Friday or Tuesday, Thursday, Saturday','23-25 students','The classroom is equipped with air conditioning and a large screen TV.');
SELECT * FROM `Section` s ;
-- Lesson 1 - Lesson 3
INSERT INTO Lesson (course_id, lesson_number, topics_covered, outcome)
VALUES (4, 'Lesson 1 - Lesson 3', 'Practice part 1\nPeople description pictures\nObject description pictures\nMixed pictures', 'Ability to describe situations related to people, including appearance, emotional states, and daily activities.\nCapability to describe and identify common objects through images, such as utensils, everyday items, and more.');

-- Lesson 4 - Lesson 8
INSERT INTO Lesson (course_id, lesson_number, topics_covered, outcome)
VALUES (4, 'Lesson 4 - Lesson 8', 'Practice Part 2', 'Learn about everyday communication situations, including discussing work and daily life. Expand vocabulary related to topics such as travel, shopping, and entertainment. Practice listening skills and understanding content from simple and useful dialogues.');

-- Lesson 9 - Lesson 19
INSERT INTO Lesson (course_id, lesson_number, topics_covered, outcome)
VALUES (4, 'Lesson 9 - Lesson 19', 'Practice Part 3\nGrammar Part 5-6', 'Study basic grammar structures such as present simple, past simple, and conditional sentences. Apply grammar rules to enhance accuracy and confidence in speaking and writing English.');

-- Lesson 20 - Lesson 26
INSERT INTO Lesson (course_id, lesson_number, topics_covered, outcome)
VALUES (4, 'Lesson 20 - Lesson 26', 'Practice part 4\nGrammar Part 5-6\nPractice tests', 'Develop the ability to synthesize and apply learned knowledge in real-life situations. Familiarize yourself with high-applicability exercises, such as reading and evaluating information from short passages and articles.');

-- Lesson 27
INSERT INTO Lesson (course_id, lesson_number, topics_covered, outcome)
VALUES (4, 'Lesson 27', 'Final test', 'Comprehensive assessment of listening, reading, speaking, and writing skills in the TOEIC exam format. Evaluate personal progress and determine readiness for the actual exam.');

-- TOEIC PRACTICE 2 skills
INSERT INTO Section(course_id, goalTitle, contentGoal, introduce, contentIntroduce, details, contentDetails, countLessons, durationLesson, supportTime, classSize, contentClassSize)
values (5,
        'Goal: Redouble 50-70 Toeic pratice',
        'Students will obtain a TOEIC certificate at 110 within 2 months after the course ends (to ensure sufficient knowledge retention for the exam, a free 100% retake is offered if the result is below 550).\n Additionally, students must meet the attendance and assignment requirements as per the class regulations.',
        'This course is for those who have a foundation but have never studied TOEIC or have studied but scored below 600.',
        'The course will focus on essential TOEIC tasks, covering all listening sections (part 1-2-3-4), while simultaneously reviewing basic grammar and introducing fundamental reading sections. With over 15 vocabulary topics through FLASHCARDS, students will quickly expand their vocabulary. After the course, students typically achieve a score of 550+. This vocabulary set is compiled from ETS TOEIC exam materials to facilitate learning.',
        27,
        'Complete all listening sections (part 1-2-3-4)\nReview basic grammar\nFamiliarize with basic reading sections',
        '3 lessons per week','1.5 hours per lesson',
        'Shift 1: 18:00-18:30, Shift 2: 20:00-21:30 on evenings of Monday, Wednesday, Friday or Tuesday, Thursday, Saturday','23-25 students','The classroom is equipped with air conditioning and a large screen TV.');
SELECT * FROM `Section` s ;
-- Lesson 1 - Lesson 3
INSERT INTO Lesson (course_id, lesson_number, topics_covered, outcome)
VALUES (5, 'Lesson 1 - Lesson 3', 'Practice part 1\nPeople description pictures\nObject description pictures\nMixed pictures', 'Ability to describe situations related to people, including appearance, emotional states, and daily activities.\nCapability to describe and identify common objects through images, such as utensils, everyday items, and more.');

-- Lesson 4 - Lesson 8
INSERT INTO Lesson (course_id, lesson_number, topics_covered, outcome)
VALUES (5, 'Lesson 4 - Lesson 8', 'Practice Part 2', 'Learn about everyday communication situations, including discussing work and daily life. Expand vocabulary related to topics such as travel, shopping, and entertainment. Practice listening skills and understanding content from simple and useful dialogues.');

-- Lesson 9 - Lesson 19
INSERT INTO Lesson (course_id, lesson_number, topics_covered, outcome)
VALUES (5, 'Lesson 9 - Lesson 19', 'Practice Part 3\nGrammar Part 5-6', 'Study basic grammar structures such as present simple, past simple, and conditional sentences. Apply grammar rules to enhance accuracy and confidence in speaking and writing English.');

-- Lesson 20 - Lesson 26
INSERT INTO Lesson (course_id, lesson_number, topics_covered, outcome)
VALUES (5, 'Lesson 20 - Lesson 26', 'Practice part 4\nGrammar Part 5-6\nPractice tests', 'Develop the ability to synthesize and apply learned knowledge in real-life situations. Familiarize yourself with high-applicability exercises, such as reading and evaluating information from short passages and articles.');

-- Lesson 27
INSERT INTO Lesson (course_id, lesson_number, topics_covered, outcome)
VALUES (5, 'Lesson 27', 'Final test', 'Comprehensive assessment of listening, reading, speaking, and writing skills in the TOEIC exam format. Evaluate personal progress and determine readiness for the actual exam.');

-- TOEIC 4 Skills: Toeic Basic
INSERT INTO Section(course_id, goalTitle, contentGoal, introduce, contentIntroduce, details, contentDetails, countLessons, durationLesson, supportTime, classSize, contentClassSize)
VALUES (6,
        'Goal: 250+ TOEIC Basic',
        'Students will obtain a TOEIC certificate at 110 within 2 months after the course ends (to ensure sufficient knowledge retention for the exam, a free 100% retake is offered if the result is below 550).\n Additionally, students must meet the attendance and assignment requirements as per the class regulations.',
        'This course is for those who have a foundation but have never studied TOEIC or have studied but scored below 600.',
        'The course will focus on essential TOEIC tasks, covering all listening sections (part 1-2-3-4), while simultaneously reviewing basic grammar and introducing fundamental reading sections. With over 15 vocabulary topics through FLASHCARDS, students will quickly expand their vocabulary. After the course, students typically achieve a score of 550+. This vocabulary set is compiled from ETS TOEIC exam materials to facilitate learning.',
        27,
        'Complete all listening sections (part 1-2-3-4)\nReview basic grammar\nFamiliarize with basic reading sections',
        '3 lessons per week','1.5 hours per lesson',
        'Shift 1: 18:00-18:30, Shift 2: 20:00-21:30 on evenings of Monday, Wednesday, Friday or Tuesday, Thursday, Saturday','23-25 students','The classroom is equipped with air conditioning and a large screen TV.');

-- Lessons for TOEIC Basic 4 Skills
INSERT INTO Lesson (course_id, lesson_number, topics_covered, outcome)
VALUES (6, 'Lesson 1 - Lesson 3', 'Practice part 1\nPeople description pictures\nObject description pictures\nMixed pictures', 'Ability to describe situations related to people, including appearance, emotional states, and daily activities.\nCapability to describe and identify common objects through images, such as utensils, everyday items, and more.');

INSERT INTO Lesson (course_id, lesson_number, topics_covered, outcome)
VALUES (6, 'Lesson 4 - Lesson 8', 'Practice Part 2', 'Learn about everyday communication situations, including discussing work and daily life. Expand vocabulary related to topics such as travel, shopping, and entertainment. Practice listening skills and understanding content from simple and useful dialogues.');

INSERT INTO Lesson (course_id, lesson_number, topics_covered, outcome)
VALUES (6, 'Lesson 9 - Lesson 19', 'Practice Part 3\nGrammar Part 5-6', 'Study basic grammar structures such as present simple, past simple, and conditional sentences. Apply grammar rules to enhance accuracy and confidence in speaking and writing English.');

INSERT INTO Lesson (course_id, lesson_number, topics_covered, outcome)
VALUES (6, 'Lesson 20 - Lesson 26', 'Practice part 4\nGrammar Part 5-6\nPractice tests', 'Develop the ability to synthesize and apply learned knowledge in real-life situations. Familiarize yourself with high-applicability exercises, such as reading and evaluating information from short passages and articles.');

INSERT INTO Lesson (course_id, lesson_number, topics_covered, outcome)
VALUES (6, 'Lesson 27', 'Final test', 'Comprehensive assessment of listening, reading, speaking, and writing skills in the TOEIC exam format. Evaluate personal progress and determine readiness for the actual exam.');

-- TOEIC 4 Skills: Toeic Pre
INSERT INTO Section(course_id, goalTitle, contentGoal, introduce, contentIntroduce, details, contentDetails, countLessons, durationLesson, supportTime, classSize, contentClassSize)
VALUES (7,
        'Goal: 350+ TOEIC PRE',
        'Students will obtain a TOEIC certificate at 110 within 2 months after the course ends (to ensure sufficient knowledge retention for the exam, a free 100% retake is offered if the result is below 550).\n Additionally, students must meet the attendance and assignment requirements as per the class regulations.',
        'This course is for those who have a foundation but have never studied TOEIC or have studied but scored below 600.',
        'The course will focus on essential TOEIC tasks, covering all listening sections (part 1-2-3-4), while simultaneously reviewing basic grammar and introducing fundamental reading sections. With over 15 vocabulary topics through FLASHCARDS, students will quickly expand their vocabulary. After the course, students typically achieve a score of 550+. This vocabulary set is compiled from ETS TOEIC exam materials to facilitate learning.',
        27,
        'Complete all listening sections (part 1-2-3-4)\nReview basic grammar\nFamiliarize with basic reading sections',
        '3 lessons per week','1.5 hours per lesson',
        'Shift 1: 18:00-18:30, Shift 2: 20:00-21:30 on evenings of Monday, Wednesday, Friday or Tuesday, Thursday, Saturday','23-25 students','The classroom is equipped with air conditioning and a large screen TV.');

-- Lessons for TOEIC Pre 4 Skills
INSERT INTO Lesson (course_id, lesson_number, topics_covered, outcome)
VALUES (7, 'Lesson 1 - Lesson 3', 'Practice part 1\nPeople description pictures\nObject description pictures\nMixed pictures', 'Ability to describe situations related to people, including appearance, emotional states, and daily activities.\nCapability to describe and identify common objects through images, such as utensils, everyday items, and more.');

INSERT INTO Lesson (course_id, lesson_number, topics_covered, outcome)
VALUES (7, 'Lesson 4 - Lesson 8', 'Practice Part 2', 'Learn about everyday communication situations, including discussing work and daily life. Expand vocabulary related to topics such as travel, shopping, and entertainment. Practice listening skills and understanding content from simple and useful dialogues.');

INSERT INTO Lesson (course_id, lesson_number, topics_covered, outcome)
VALUES (7, 'Lesson 9 - Lesson 19', 'Practice Part 3\nGrammar Part 5-6', 'Study basic grammar structures such as present simple, past simple, and conditional sentences. Apply grammar rules to enhance accuracy and confidence in speaking and writing English.');

INSERT INTO Lesson (course_id, lesson_number, topics_covered, outcome)
VALUES (7, 'Lesson 20 - Lesson 26', 'Practice part 4\nGrammar Part 5-6\nPractice tests', 'Develop the ability to synthesize and apply learned knowledge in real-life situations. Familiarize yourself with high-applicability exercises, such as reading and evaluating information from short passages and articles.');

INSERT INTO Lesson (course_id, lesson_number, topics_covered, outcome)
VALUES (7, 'Lesson 27', 'Final test', 'Comprehensive assessment of listening, reading, speaking, and writing skills in the TOEIC exam format. Evaluate personal progress and determine readiness for the actual exam.');

-- TOEIC 4 Skills: Toeic A
INSERT INTO Section(course_id, goalTitle, contentGoal, introduce, contentIntroduce, details, contentDetails, countLessons, durationLesson, supportTime, classSize, contentClassSize)
VALUES (8,
        'Goal: 500+ TOEIC A',
        'Students will obtain a TOEIC certificate at 110 within 2 months after the course ends (to ensure sufficient knowledge retention for the exam, a free 100% retake is offered if the result is below 550).\n Additionally, students must meet the attendance and assignment requirements as per the class regulations.',
        'This course is for those who have a foundation but have never studied TOEIC or have studied but scored below 600.',
        'The course will focus on essential TOEIC tasks, covering all listening sections (part 1-2-3-4), while simultaneously reviewing basic grammar and introducing fundamental reading sections. With over 15 vocabulary topics through FLASHCARDS, students will quickly expand their vocabulary. After the course, students typically achieve a score of 550+. This vocabulary set is compiled from ETS TOEIC exam materials to facilitate learning.',
        27,
        'Complete all listening sections (part 1-2-3-4)\nReview basic grammar\nFamiliarize with basic reading sections',
        '3 lessons per week','1.5 hours per lesson',
        'Shift 1: 18:00-18:30, Shift 2: 20:00-21:30 on evenings of Monday, Wednesday, Friday or Tuesday, Thursday, Saturday','23-25 students','The classroom is equipped with air conditioning and a large screen TV.');

-- Lessons for TOEIC A 4 Skills
INSERT INTO Lesson (course_id, lesson_number, topics_covered, outcome)
VALUES (8, 'Lesson 1 - Lesson 3', 'Practice part 1\nPeople description pictures\nObject description pictures\nMixed pictures', 'Ability to describe situations related to people, including appearance, emotional states, and daily activities.\nCapability to describe and identify common objects through images, such as utensils, everyday items, and more.');

INSERT INTO Lesson (course_id, lesson_number, topics_covered, outcome)
VALUES (8, 'Lesson 4 - Lesson 8', 'Practice Part 2', 'Learn about everyday communication situations, including discussing work and daily life. Expand vocabulary related to topics such as travel, shopping, and entertainment. Practice listening skills and understanding content from simple and useful dialogues.');

INSERT INTO Lesson (course_id, lesson_number, topics_covered, outcome)
VALUES (8, 'Lesson 9 - Lesson 19', 'Practice Part 3\nGrammar Part 5-6', 'Study basic grammar structures such as present simple, past simple, and conditional sentences. Apply grammar rules to enhance accuracy and confidence in speaking and writing English.');

INSERT INTO Lesson (course_id, lesson_number, topics_covered, outcome)
VALUES (8, 'Lesson 20 - Lesson 26', 'Practice part 4\nGrammar Part 5-6\nPractice tests', 'Develop the ability to synthesize and apply learned knowledge in real-life situations. Familiarize yourself with high-applicability exercises, such as reading and evaluating information from short passages and articles.');

INSERT INTO Lesson (course_id, lesson_number, topics_covered, outcome)
VALUES (8, 'Lesson 27', 'Final test', 'Comprehensive assessment of listening, reading, speaking, and writing skills in the TOEIC exam format. Evaluate personal progress and determine readiness for the actual exam.');

-- TOEIC 4 Skills: Toeic A+
INSERT INTO Section(course_id, goalTitle, contentGoal, introduce, contentIntroduce, details, contentDetails, countLessons, durationLesson, supportTime, classSize, contentClassSize)
VALUES (9,
        'Goal: 70-110/200 Writing&Speaking + TOEIC A+',
        'Students will obtain a TOEIC certificate at 110 within 2 months after the course ends (to ensure sufficient knowledge retention for the exam, a free 100% retake is offered if the result is below 550).\n Additionally, students must meet the attendance and assignment requirements as per the class regulations.',
        'This course is for those who have a foundation but have never studied TOEIC or have studied but scored below 600.',
        'The course will focus on essential TOEIC tasks, covering all listening sections (part 1-2-3-4), while simultaneously reviewing basic grammar and introducing fundamental reading sections. With over 15 vocabulary topics through FLASHCARDS, students will quickly expand their vocabulary. After the course, students typically achieve a score of 550+. This vocabulary set is compiled from ETS TOEIC exam materials to facilitate learning.',
        27,
        'Complete all listening sections (part 1-2-3-4)\nReview basic grammar\nFamiliarize with basic reading sections',
        '3 lessons per week','1.5 hours per lesson',
        'Shift 1: 18:00-18:30, Shift 2: 20:00-21:30 on evenings of Monday, Wednesday, Friday or Tuesday, Thursday, Saturday','23-25 students','The classroom is equipped with air conditioning and a large screen TV.');

-- Lessons for TOEIC A+ 4 Skills
INSERT INTO Lesson (course_id, lesson_number, topics_covered, outcome)
VALUES (9, 'Lesson 1 - Lesson 3', 'Practice part 1\nPeople description pictures\nObject description pictures\nMixed pictures', 'Ability to describe situations related to people, including appearance, emotional states, and daily activities.\nCapability to describe and identify common objects through images, such as utensils, everyday items, and more.');

INSERT INTO Lesson (course_id, lesson_number, topics_covered, outcome)
VALUES (9, 'Lesson 4 - Lesson 8', 'Practice Part 2', 'Learn about everyday communication situations, including discussing work and daily life. Expand vocabulary related to topics such as travel, shopping, and entertainment. Practice listening skills and understanding content from simple and useful dialogues.');

INSERT INTO Lesson (course_id, lesson_number, topics_covered, outcome)
VALUES (9, 'Lesson 9 - Lesson 19', 'Practice Part 3\nGrammar Part 5-6', 'Study basic grammar structures such as present simple, past simple, and conditional sentences. Apply grammar rules to enhance accuracy and confidence in speaking and writing English.');

INSERT INTO Lesson (course_id, lesson_number, topics_covered, outcome)
VALUES (9, 'Lesson 20 - Lesson 26', 'Practice part 4\nGrammar Part 5-6\nPractice tests', 'Develop the ability to synthesize and apply learned knowledge in real-life situations. Familiarize yourself with high-applicability exercises, such as reading and evaluating information from short passages and articles.');

INSERT INTO Lesson (course_id, lesson_number, topics_covered, outcome)
VALUES (9, 'Lesson 27', 'Final test', 'Comprehensive assessment of listening, reading, speaking, and writing skills in the TOEIC exam format. Evaluate personal progress and determine readiness for the actual exam.');

-- TOEIC 4 Skills: Toeic B
INSERT INTO Section(course_id, goalTitle, contentGoal, introduce, contentIntroduce, details, contentDetails, countLessons, durationLesson, supportTime, classSize, contentClassSize)
VALUES (10,
        'Goal: 650+ TOEIC B',
        'Students will obtain a TOEIC certificate at 110 within 2 months after the course ends (to ensure sufficient knowledge retention for the exam, a free 100% retake is offered if the result is below 550).\n Additionally, students must meet the attendance and assignment requirements as per the class regulations.',
        'This course is for those who have a foundation but have never studied TOEIC or have studied but scored below 600.',
        'The course will focus on essential TOEIC tasks, covering all listening sections (part 1-2-3-4), while simultaneously reviewing basic grammar and introducing fundamental reading sections. With over 15 vocabulary topics through FLASHCARDS, students will quickly expand their vocabulary. After the course, students typically achieve a score of 550+. This vocabulary set is compiled from ETS TOEIC exam materials to facilitate learning.',
        27,
        'Complete all listening sections (part 1-2-3-4)\nReview basic grammar\nFamiliarize with basic reading sections',
        '3 lessons per week','1.5 hours per lesson',
        'Shift 1: 18:00-18:30, Shift 2: 20:00-21:30 on evenings of Monday, Wednesday, Friday or Tuesday, Thursday, Saturday','23-25 students','The classroom is equipped with air conditioning and a large screen TV.');

-- Lessons for TOEIC B 4 Skills
INSERT INTO Lesson (course_id, lesson_number, topics_covered, outcome)
VALUES (10, 'Lesson 1 - Lesson 3', 'Practice part 1\nPeople description pictures\nObject description pictures\nMixed pictures', 'Ability to describe situations related to people, including appearance, emotional states, and daily activities.\nCapability to describe and identify common objects through images, such as utensils, everyday items, and more.');

INSERT INTO Lesson (course_id, lesson_number, topics_covered, outcome)
VALUES (10, 'Lesson 4 - Lesson 8', 'Practice Part 2', 'Learn about everyday communication situations, including discussing work and daily life. Expand vocabulary related to topics such as travel, shopping, and entertainment. Practice listening skills and understanding content from simple and useful dialogues.');

INSERT INTO Lesson (course_id, lesson_number, topics_covered, outcome)
VALUES (10, 'Lesson 9 - Lesson 19', 'Practice Part 3\nGrammar Part 5-6', 'Study basic grammar structures such as present simple, past simple, and conditional sentences. Apply grammar rules to enhance accuracy and confidence in speaking and writing English.');

INSERT INTO Lesson (course_id, lesson_number, topics_covered, outcome)
VALUES (10, 'Lesson 20 - Lesson 26', 'Practice part 4\nGrammar Part 5-6\nPractice tests', 'Develop the ability to synthesize and apply learned knowledge in real-life situations. Familiarize yourself with high-applicability exercises, such as reading and evaluating information from short passages and articles.');

INSERT INTO Lesson (course_id, lesson_number, topics_covered, outcome)
VALUES (10, 'Lesson 27', 'Final test', 'Comprehensive assessment of listening, reading, speaking, and writing skills in the TOEIC exam format. Evaluate personal progress and determine readiness for the actual exam.');

-- TOEIC 4 Skills: Toeic B+
INSERT INTO Section(course_id, goalTitle, contentGoal, introduce, contentIntroduce, details, contentDetails, countLessons, durationLesson, supportTime, classSize, contentClassSize)
VALUES (11,
        'Goal: 110/150/200 TOEIC B+',
        'Students will obtain a TOEIC certificate at 110 within 2 months after the course ends (to ensure sufficient knowledge retention for the exam, a free 100% retake is offered if the result is below 550).\n Additionally, students must meet the attendance and assignment requirements as per the class regulations.',
        'This course is for those who have a foundation but have never studied TOEIC or have studied but scored below 600.',
        'The course will focus on essential TOEIC tasks, covering all listening sections (part 1-2-3-4), while simultaneously reviewing basic grammar and introducing fundamental reading sections. With over 15 vocabulary topics through FLASHCARDS, students will quickly expand their vocabulary. After the course, students typically achieve a score of 550+. This vocabulary set is compiled from ETS TOEIC exam materials to facilitate learning.',
        27,
        'Complete all listening sections (part 1-2-3-4)\nReview basic grammar\nFamiliarize with basic reading sections',
        '3 lessons per week','1.5 hours per lesson',
        'Shift 1: 18:00-18:30, Shift 2: 20:00-21:30 on evenings of Monday, Wednesday, Friday or Tuesday, Thursday, Saturday','23-25 students','The classroom is equipped with air conditioning and a large screen TV.');

-- Lessons for TOEIC B+ 4 Skills
INSERT INTO Lesson (course_id, lesson_number, topics_covered, outcome)
VALUES (11, 'Lesson 1 - Lesson 3', 'Practice part 1\nPeople description pictures\nObject description pictures\nMixed pictures', 'Ability to describe situations related to people, including appearance, emotional states, and daily activities.\nCapability to describe and identify common objects through images, such as utensils, everyday items, and more.');

INSERT INTO Lesson (course_id, lesson_number, topics_covered, outcome)
VALUES (11, 'Lesson 4 - Lesson 8', 'Practice Part 2', 'Learn about everyday communication situations, including discussing work and daily life. Expand vocabulary related to topics such as travel, shopping, and entertainment. Practice listening skills and understanding content from simple and useful dialogues.');

INSERT INTO Lesson (course_id, lesson_number, topics_covered, outcome)
VALUES (11, 'Lesson 9 - Lesson 19', 'Practice Part 3\nGrammar Part 5-6', 'Study basic grammar structures such as present simple, past simple, and conditional sentences. Apply grammar rules to enhance accuracy and confidence in speaking and writing English.');

INSERT INTO Lesson (course_id, lesson_number, topics_covered, outcome)
VALUES (11, 'Lesson 20 - Lesson 26', 'Practice part 4\nGrammar Part 5-6\nPractice tests', 'Develop the ability to synthesize and apply learned knowledge in real-life situations. Familiarize yourself with high-applicability exercises, such as reading and evaluating information from short passages and articles.');

INSERT INTO Lesson (course_id, lesson_number, topics_covered, outcome)
VALUES (11, 'Lesson 27', 'Final test', 'Comprehensive assessment of listening, reading, speaking, and writing skills in the TOEIC exam format. Evaluate personal progress and determine readiness for the actual exam.');

-- TOEIC 4 Skills: Toeic Training
INSERT INTO Section(course_id, goalTitle, contentGoal, introduce, contentIntroduce, details, contentDetails, countLessons, durationLesson, supportTime, classSize, contentClassSize)
VALUES (12,
        'Goal: Redouble 50-70 TOEIC Training',
        'Students will obtain a TOEIC certificate at 110 within 2 months after the course ends (to ensure sufficient knowledge retention for the exam, a free 100% retake is offered if the result is below 550).\n Additionally, students must meet the attendance and assignment requirements as per the class regulations.',
        'This course is for those who have a foundation but have never studied TOEIC or have studied but scored below 600.',
        'The course will focus on essential TOEIC tasks, covering all listening sections (part 1-2-3-4), while simultaneously reviewing basic grammar and introducing fundamental reading sections. With over 15 vocabulary topics through FLASHCARDS, students will quickly expand their vocabulary. After the course, students typically achieve a score of 550+. This vocabulary set is compiled from ETS TOEIC exam materials to facilitate learning.',
        27,
        'Complete all listening sections (part 1-2-3-4)\nReview basic grammar\nFamiliarize with basic reading sections',
        '3 lessons per week','1.5 hours per lesson',
        'Shift 1: 18:00-18:30, Shift 2: 20:00-21:30 on evenings of Monday, Wednesday, Friday or Tuesday, Thursday, Saturday','23-25 students','The classroom is equipped with air conditioning and a large screen TV.');

-- Lessons for TOEIC Training 4 Skills
INSERT INTO Lesson (course_id, lesson_number, topics_covered, outcome)
VALUES (12, 'Lesson 1 - Lesson 3', 'Practice part 1\nPeople description pictures\nObject description pictures\nMixed pictures', 'Ability to describe situations related to people, including appearance, emotional states, and daily activities.\nCapability to describe and identify common objects through images, such as utensils, everyday items, and more.');

INSERT INTO Lesson (course_id, lesson_number, topics_covered, outcome)
VALUES (12, 'Lesson 4 - Lesson 8', 'Practice Part 2', 'Learn about everyday communication situations, including discussing work and daily life. Expand vocabulary related to topics such as travel, shopping, and entertainment. Practice listening skills and understanding content from simple and useful dialogues.');

INSERT INTO Lesson (course_id, lesson_number, topics_covered, outcome)
VALUES (12, 'Lesson 9 - Lesson 19', 'Practice Part 3\nGrammar Part 5-6', 'Study basic grammar structures such as present simple, past simple, and conditional sentences. Apply grammar rules to enhance accuracy and confidence in speaking and writing English.');

INSERT INTO Lesson (course_id, lesson_number, topics_covered, outcome)
VALUES (12, 'Lesson 20 - Lesson 26', 'Practice part 4\nGrammar Part 5-6\nPractice tests', 'Develop the ability to synthesize and apply learned knowledge in real-life situations. Familiarize yourself with high-applicability exercises, such as reading and evaluating information from short passages and articles.');

INSERT INTO Lesson (course_id, lesson_number, topics_covered, outcome)
VALUES (12, 'Lesson 27', 'Final test', 'Comprehensive assessment of listening, reading, speaking, and writing skills in the TOEIC exam format. Evaluate personal progress and determine readiness for the actual exam.');

-- IELTS: Ielts Basic
INSERT INTO Section(course_id, goalTitle, contentGoal, introduce, contentIntroduce, details, contentDetails, countLessons, durationLesson, supportTime, classSize, contentClassSize)
VALUES (13,
        'Goal: IELTS 2.5+',
        'Students will achieve a basic understanding of the IELTS test format and essential strategies to approach each section of the exam.',
        'This course is designed for students who are new to IELTS or have a very basic understanding of English.',
        'The course focuses on fundamental skills in listening, reading, writing, and speaking. Vocabulary building and grammar review are integral parts of the course, providing a solid foundation for further study.',
        20,
        'Basic skills in listening, reading, writing, and speaking\nVocabulary building\nGrammar review',
        '2 lessons per week','1.5 hours per lesson',
        'Shift 1: 18:00-19:30, Shift 2: 20:00-21:30 on Monday and Wednesday or Tuesday and Thursday','20-22 students','Classroom is equipped with air conditioning and a projector.');

-- Lessons for Ielts Basic
INSERT INTO Lesson (course_id, lesson_number, topics_covered, outcome)
VALUES (13, 'Lesson 1 - Lesson 5', 'Introduction to IELTS\nListening Basics\nReading Basics', 'Understand the structure of the IELTS test and basic strategies for listening and reading sections.');

INSERT INTO Lesson (course_id, lesson_number, topics_covered, outcome)
VALUES (13, 'Lesson 6 - Lesson 10', 'Writing Basics\nSpeaking Basics', 'Learn fundamental writing techniques and speaking skills for the IELTS test.');

INSERT INTO Lesson (course_id, lesson_number, topics_covered, outcome)
VALUES (13, 'Lesson 11 - Lesson 15', 'Vocabulary Building\nGrammar Review', 'Enhance vocabulary and review essential grammar points for the IELTS test.');

INSERT INTO Lesson (course_id, lesson_number, topics_covered, outcome)
VALUES (13, 'Lesson 16 - Lesson 20', 'Practice Tests\nReview Sessions', 'Apply learned skills in practice tests and receive feedback to improve.');

-- IELTS: Ielts Pre
INSERT INTO Section(course_id, goalTitle, contentGoal, introduce, contentIntroduce, details, contentDetails, countLessons, durationLesson, supportTime, classSize, contentClassSize)
VALUES (14,
        'Goal: IELTS 3.5+',
        'Students will improve their English proficiency and develop intermediate strategies for each section of the IELTS test.',
        'This course is suitable for students who have a basic understanding of English and aim to score around 5.0 in the IELTS test.',
        'The course covers intermediate skills in listening, reading, writing, and speaking. It includes extensive practice sessions and mock tests to build confidence and exam readiness.',
        25,
        'Intermediate skills in listening, reading, writing, and speaking\nExtensive practice sessions\nMock tests',
        '3 lessons per week','1.5 hours per lesson',
        'Shift 1: 18:00-19:30, Shift 2: 20:00-21:30 on Monday, Wednesday, Friday or Tuesday, Thursday, Saturday','20-22 students','Classroom is equipped with air conditioning and a projector.');

-- Lessons for Ielts Pre
INSERT INTO Lesson (course_id, lesson_number, topics_covered, outcome)
VALUES (14, 'Lesson 1 - Lesson 5', 'Listening Intermediate\nReading Intermediate', 'Develop intermediate listening and reading skills for the IELTS test.');

INSERT INTO Lesson (course_id, lesson_number, topics_covered, outcome)
VALUES (14, 'Lesson 6 - Lesson 10', 'Writing Intermediate\nSpeaking Intermediate', 'Enhance writing and speaking skills with a focus on intermediate-level tasks.');

INSERT INTO Lesson (course_id, lesson_number, topics_covered, outcome)
VALUES (14, 'Lesson 11 - Lesson 15', 'Vocabulary Expansion\nGrammar Improvement', 'Expand vocabulary and improve grammar knowledge for better performance in the IELTS test.');

INSERT INTO Lesson (course_id, lesson_number, topics_covered, outcome)
VALUES (14, 'Lesson 16 - Lesson 20', 'Mock Tests\nFeedback Sessions', 'Take mock tests and receive detailed feedback to identify areas of improvement.');

INSERT INTO Lesson (course_id, lesson_number, topics_covered, outcome)
VALUES (14, 'Lesson 21 - Lesson 25', 'Final Practice\nExam Strategies', 'Focus on final practice sessions and learn effective exam strategies for the IELTS test.');

-- IELTS: Ielts Pre Foundation
INSERT INTO Section(course_id, goalTitle, contentGoal, introduce, contentIntroduce, details, contentDetails, countLessons, durationLesson, supportTime, classSize, contentClassSize)
VALUES (15,
        'Goal: IELTS 4.5+',
        'Students will gain advanced skills and strategies needed to achieve a higher score in the IELTS test.',
        'This course is designed for students who have an intermediate understanding of English and aim to score around 6.0 in the IELTS test.',
        'The course covers advanced skills in listening, reading, writing, and speaking. It includes intensive practice and personalized feedback to address individual weaknesses.',
        30,
        'Advanced skills in listening, reading, writing, and speaking\nIntensive practice\nPersonalized feedback',
        '3 lessons per week','1.5 hours per lesson',
        'Shift 1: 18:00-19:30, Shift 2: 20:00-21:30 on Monday, Wednesday, Friday or Tuesday, Thursday, Saturday','18-20 students','Classroom is equipped with air conditioning and a projector.');

-- Lessons for Ielts Pre Foundation
INSERT INTO Lesson (course_id, lesson_number, topics_covered, outcome)
VALUES (15, 'Lesson 1 - Lesson 5', 'Advanced Listening\nAdvanced Reading', 'Master advanced listening and reading skills for the IELTS test.');

INSERT INTO Lesson (course_id, lesson_number, topics_covered, outcome)
VALUES (15, 'Lesson 6 - Lesson 10', 'Advanced Writing\nAdvanced Speaking', 'Develop advanced writing and speaking skills for the IELTS test.');

INSERT INTO Lesson (course_id, lesson_number, topics_covered, outcome)
VALUES (15, 'Lesson 11 - Lesson 15', 'Intensive Vocabulary\nGrammar Mastery', 'Expand advanced vocabulary and achieve grammar mastery for the IELTS test.');

INSERT INTO Lesson (course_id, lesson_number, topics_covered, outcome)
VALUES (15, 'Lesson 16 - Lesson 20', 'Practice Sessions\nMock Tests', 'Engage in intensive practice sessions and take mock tests with detailed feedback.');

INSERT INTO Lesson (course_id, lesson_number, topics_covered, outcome)
VALUES (15, 'Lesson 21 - Lesson 25', 'Targeted Feedback\nWeakness Improvement', 'Receive targeted feedback and work on improving individual weaknesses.');

INSERT INTO Lesson (course_id, lesson_number, topics_covered, outcome)
VALUES (15, 'Lesson 26 - Lesson 30', 'Final Preparation\nExam Techniques', 'Prepare for the final exam with focused practice and learn essential exam techniques.');

-- IELTS: Ielts Foundation
INSERT INTO Section(course_id, goalTitle, contentGoal, introduce, contentIntroduce, details, contentDetails, countLessons, durationLesson, supportTime, classSize, contentClassSize)
VALUES (16,
        'Goal: IELTS 5.5+',
        'Students will build upon their existing knowledge to achieve a score of 6.5 or higher in the IELTS test.',
        'This course is suitable for students who have a good understanding of English and aim to score around 6.5 in the IELTS test.',
        'The course includes comprehensive training in all four skills, with a strong emphasis on practice and application of advanced techniques.',
        35,
        'Comprehensive training in listening, reading, writing, and speaking\nAdvanced techniques\nExtensive practice',
        '3 lessons per week','1.5 hours per lesson',
        'Shift 1: 18:00-19:30, Shift 2: 20:00-21:30 on Monday, Wednesday, Friday or Tuesday, Thursday, Saturday','18-20 students','Classroom is equipped with air conditioning and a projector.');

-- Lessons for Ielts Foundation
INSERT INTO Lesson (course_id, lesson_number, topics_covered, outcome)
VALUES (16, 'Lesson 1 - Lesson 5', 'Listening Techniques\nReading Techniques', 'Acquire advanced techniques for the listening and reading sections of the IELTS test.');

INSERT INTO Lesson (course_id, lesson_number, topics_covered, outcome)
VALUES (16, 'Lesson 6 - Lesson 10', 'Writing Techniques\nSpeaking Techniques', 'Master advanced techniques for the writing and speaking sections of the IELTS test.');

INSERT INTO Lesson (course_id, lesson_number, topics_covered, outcome)
VALUES (16, 'Lesson 11 - Lesson 15', 'Intensive Grammar\nAdvanced Vocabulary', 'Achieve a high level of grammar accuracy and expand advanced vocabulary for the IELTS test.');

INSERT INTO Lesson (course_id, lesson_number, topics_covered, outcome)
VALUES (16, 'Lesson 16 - Lesson 20', 'Practice Sessions\nMock Tests', 'Engage in extensive practice sessions and take mock tests with personalized feedback.');

INSERT INTO Lesson (course_id, lesson_number, topics_covered, outcome)
VALUES (16, 'Lesson 21 - Lesson 25', 'Individual Feedback\nFocus Areas', 'Receive individual feedback and focus on improving specific areas of weakness.');

INSERT INTO Lesson (course_id, lesson_number, topics_covered, outcome)
VALUES (16, 'Lesson 26 - Lesson 30', 'Final Preparation\nExam Techniques', 'Finalize exam preparation with targeted practice and learn essential exam techniques.');

INSERT INTO Lesson (course_id, lesson_number, topics_covered, outcome)
VALUES (16, 'Lesson 31 - Lesson 35', 'Final Mock Test\nReview', 'Take a final mock test and review all sections to ensure readiness for the actual exam.');

-- IELTS: Ielts Speaking/Writing A
INSERT INTO Section(course_id, goalTitle, contentGoal, introduce, contentIntroduce, details, contentDetails, countLessons, durationLesson, supportTime, classSize, contentClassSize)
VALUES (17,
        'Goal: Improve Speaking and Writing Skills',
        'Students will develop specific strategies and techniques to excel in the speaking and writing sections of the IELTS test.',
        'This course is designed for students who need focused practice in speaking and writing to achieve a higher score in these sections.',
        'The course includes intensive practice sessions, personalized feedback, and targeted skill development in speaking and writing.',
        20,
        'Focused practice in speaking and writing\nPersonalized feedback\nTargeted skill development',
        '2 lessons per week','1.5 hours per lesson',
        'Shift 1: 18:00-19:30, Shift 2: 20:00-21:30 on Monday and Wednesday or Tuesday and Thursday','15-18 students','Classroom is equipped with air conditioning and a projector.');

-- Lessons for Ielts Speaking/Writing A
INSERT INTO Lesson (course_id, lesson_number, topics_covered, outcome)
VALUES (17, 'Lesson 1 - Lesson 5', 'Speaking Techniques\nPractice Sessions', 'Learn and practice advanced speaking techniques for the IELTS test.');

INSERT INTO Lesson (course_id, lesson_number, topics_covered, outcome)
VALUES (17, 'Lesson 6 - Lesson 10', 'Writing Techniques\nPractice Sessions', 'Develop advanced writing techniques and practice writing tasks for the IELTS test.');

INSERT INTO Lesson (course_id, lesson_number, topics_covered, outcome)
VALUES (17, 'Lesson 11 - Lesson 15', 'Mock Tests\nFeedback Sessions', 'Engage in mock tests for speaking and writing and receive personalized feedback.');

INSERT INTO Lesson (course_id, lesson_number, topics_covered, outcome)
VALUES (17, 'Lesson 16 - Lesson 20', 'Final Practice\nExam Techniques', 'Focus on final practice sessions and learn essential exam techniques for speaking and writing.');

-- IELTS: Ielts A
INSERT INTO Section(course_id, goalTitle, contentGoal, introduce, contentIntroduce, details, contentDetails, countLessons, durationLesson, supportTime, classSize, contentClassSize)
VALUES (18,
        'Goal: IELTS 6.5+',
        'Students will aim to achieve a score of 7.0 or higher in the IELTS test with comprehensive training in all four skills.',
        'This course is designed for students who have a strong understanding of English and aim to score around 7.0 in the IELTS test.',
        'The course includes advanced training, intensive practice, and personalized feedback to ensure a high level of exam readiness.',
        40,
        'Advanced training in listening, reading, writing, and speaking\nIntensive practice\nPersonalized feedback',
        '3 lessons per week','1.5 hours per lesson',
        'Shift 1: 18:00-19:30, Shift 2: 20:00-21:30 on Monday, Wednesday, Friday or Tuesday, Thursday, Saturday','15-18 students','Classroom is equipped with air conditioning and a projector.');

-- Lessons for Ielts A
INSERT INTO Lesson (course_id, lesson_number, topics_covered, outcome)
VALUES (18, 'Lesson 1 - Lesson 5', 'Listening Strategies\nReading Strategies', 'Master advanced listening and reading strategies for the IELTS test.');

INSERT INTO Lesson (course_id, lesson_number, topics_covered, outcome)
VALUES (18, 'Lesson 6 - Lesson 10', 'Writing Strategies\nSpeaking Strategies', 'Develop advanced writing and speaking strategies for the IELTS test.');

INSERT INTO Lesson (course_id, lesson_number, topics_covered, outcome)
VALUES (18, 'Lesson 11 - Lesson 15', 'Grammar Review\nVocabulary Expansion', 'Review advanced grammar and expand vocabulary for the IELTS test.');

INSERT INTO Lesson (course_id, lesson_number, topics_covered, outcome)
VALUES (18, 'Lesson 16 - Lesson 20', 'Practice Sessions\nMock Tests', 'Engage in extensive practice sessions and take mock tests with personalized feedback.');

INSERT INTO Lesson (course_id, lesson_number, topics_covered, outcome)
VALUES (18, 'Lesson 21 - Lesson 25', 'Targeted Feedback\nFocus Areas', 'Receive targeted feedback and work on improving specific areas of weakness.');

INSERT INTO Lesson (course_id, lesson_number, topics_covered, outcome)
VALUES (18, 'Lesson 26 - Lesson 30', 'Final Preparation\nExam Techniques', 'Finalize exam preparation with targeted practice and learn essential exam techniques.');

INSERT INTO Lesson (course_id, lesson_number, topics_covered, outcome)
VALUES (18, 'Lesson 31 - Lesson 35', 'Final Mock Test\nReview', 'Take a final mock test and review all sections to ensure readiness for the actual exam.');

INSERT INTO Lesson (course_id, lesson_number, topics_covered, outcome)
VALUES (18, 'Lesson 36 - Lesson 40', 'Exam Day Preparation', 'Prepare for the exam day with focused practice and final tips.');

-- IELTS: Ielts Speaking/Writing B
INSERT INTO Section(course_id, goalTitle, contentGoal, introduce, contentIntroduce, details, contentDetails, countLessons, durationLesson, supportTime, classSize, contentClassSize)
VALUES (19,
        'Goal: Improve Speaking and Writing Skills',
        'Students will further develop specific strategies and techniques to excel in the speaking and writing sections of the IELTS test.',
        'This course is designed for students who need additional focused practice in speaking and writing to achieve a higher score in these sections.',
        'The course includes more intensive practice sessions, personalized feedback, and targeted skill development in speaking and writing.',
        20,
        'Focused practice in speaking and writing\nPersonalized feedback\nTargeted skill development',
        '2 lessons per week','1.5 hours per lesson',
        'Shift 1: 18:00-19:30, Shift 2: 20:00-21:30 on Monday and Wednesday or Tuesday and Thursday','15-18 students','Classroom is equipped with air conditioning and a projector.');

-- Lessons for Ielts Speaking/Writing B
INSERT INTO Lesson (course_id, lesson_number, topics_covered, outcome)
VALUES (19, 'Lesson 1 - Lesson 5', 'Advanced Speaking Techniques\nPractice Sessions', 'Learn and practice advanced speaking techniques for the IELTS test.');

INSERT INTO Lesson (course_id, lesson_number, topics_covered, outcome)
VALUES (19, 'Lesson 6 - Lesson 10', 'Advanced Writing Techniques\nPractice Sessions', 'Develop advanced writing techniques and practice writing tasks for the IELTS test.');

INSERT INTO Lesson (course_id, lesson_number, topics_covered, outcome)
VALUES (19, 'Lesson 11 - Lesson 15', 'Mock Tests\nDetailed Feedback', 'Engage in mock tests for speaking and writing and receive personalized feedback.');

INSERT INTO Lesson (course_id, lesson_number, topics_covered, outcome)
VALUES (19, 'Lesson 16 - Lesson 20', 'Final Practice\nExam Techniques', 'Focus on final practice sessions and learn essential exam techniques for speaking and writing.');

-- IELTS: Ielts B
INSERT INTO Section(course_id, goalTitle, contentGoal, introduce, contentIntroduce, details, contentDetails, countLessons, durationLesson, supportTime, classSize, contentClassSize)
VALUES (20,
        'Goal: IELTS 7+',
        'Students will aim to achieve a score of 7.5 or higher in the IELTS test with comprehensive training in all four skills.',
        'This course is designed for students who have an advanced understanding of English and aim to score around 7.5 in the IELTS test.',
        'The course includes highly advanced training, extensive practice, and personalized feedback to ensure a high level of exam readiness.',
        40,
        'Highly advanced training in listening, reading, writing, and speaking\nExtensive practice\nPersonalized feedback',
        '3 lessons per week','1.5 hours per lesson',
        'Shift 1: 18:00-19:30, Shift 2: 20:00-21:30 on Monday, Wednesday, Friday or Tuesday, Thursday, Saturday','15-18 students','Classroom is equipped with air conditioning and a projector.');

-- Lessons for Ielts B
INSERT INTO Lesson (course_id, lesson_number, topics_covered, outcome)
VALUES (20, 'Lesson 1 - Lesson 5', 'Advanced Listening Strategies\nAdvanced Reading Strategies', 'Master highly advanced listening and reading strategies for the IELTS test.');

INSERT INTO Lesson (course_id, lesson_number, topics_covered, outcome)
VALUES (20, 'Lesson 6 - Lesson 10', 'Advanced Writing Strategies\nAdvanced Speaking Strategies', 'Develop highly advanced writing and speaking strategies for the IELTS test.');

INSERT INTO Lesson (course_id, lesson_number, topics_covered, outcome)
VALUES (20, 'Lesson 11 - Lesson 15', 'Grammar Mastery\nVocabulary Expansion', 'Achieve mastery in advanced grammar and expand advanced vocabulary for the IELTS test.');

INSERT INTO Lesson (course_id, lesson_number, topics_covered, outcome)
VALUES (20, 'Lesson 16 - Lesson 20', 'Practice Sessions\nMock Tests', 'Engage in extensive practice sessions and take mock tests with personalized feedback.');

INSERT INTO Lesson (course_id, lesson_number, topics_covered, outcome)
VALUES (20, 'Lesson 21 - Lesson 25', 'Targeted Feedback\nFocus Areas', 'Receive targeted feedback and work on improving specific areas of weakness.');

INSERT INTO Lesson (course_id, lesson_number, topics_covered, outcome)
VALUES (20, 'Lesson 26 - Lesson 30', 'Final Preparation\nExam Techniques', 'Finalize exam preparation with targeted practice and learn essential exam techniques.');

INSERT INTO Lesson (course_id, lesson_number, topics_covered, outcome)
VALUES (20, 'Lesson 31 - Lesson 35', 'Final Mock Test\nReview', 'Take a final mock test and review all sections to ensure readiness for the actual exam.');

INSERT INTO Lesson (course_id, lesson_number, topics_covered, outcome)
VALUES (20, 'Lesson 36 - Lesson 40', 'Exam Day Preparation', 'Prepare for the exam day with focused practice and final tips.');

-- IELTS: Ielts Total Review
INSERT INTO Section(course_id, goalTitle, contentGoal, introduce, contentIntroduce, details, contentDetails, countLessons, durationLesson, supportTime, classSize, contentClassSize)
VALUES (21,
        'Goal: Comprehensive IELTS Review',
        'Students will review and consolidate all necessary skills to achieve their target IELTS score.',
        'This course is designed for students who have completed their IELTS preparation and need a comprehensive review before taking the test.',
        'The course includes detailed reviews of all four skills, practice tests, and personalized feedback to ensure thorough preparation.',
        25,
        'Comprehensive review of listening, reading, writing, and speaking\nPractice tests\nPersonalized feedback',
        '2 lessons per week','2 hours per lesson',
        'Shift 1: 18:00-20:00, Shift 2: 20:00-22:00 on Monday and Wednesday or Tuesday and Thursday','15-18 students','Classroom is equipped with air conditioning and a projector.');

-- Lessons for Ielts Total Review
INSERT INTO Lesson (course_id, lesson_number, topics_covered, outcome)
VALUES (21, 'Lesson 1 - Lesson 5', 'Listening Review\nReading Review', 'Review and practice listening and reading sections for the IELTS test.');

INSERT INTO Lesson (course_id, lesson_number, topics_covered, outcome)
VALUES (21, 'Lesson 6 - Lesson 10', 'Writing Review\nSpeaking Review', 'Review and practice writing and speaking sections for the IELTS test.');

INSERT INTO Lesson (course_id, lesson_number, topics_covered, outcome)
VALUES (21, 'Lesson 11 - Lesson 15', 'Mock Tests\nFeedback Sessions', 'Take mock tests and receive detailed feedback to identify areas of improvement.');

INSERT INTO Lesson (course_id, lesson_number, topics_covered, outcome)
VALUES (21, 'Lesson 16 - Lesson 20', 'Final Practice\nReview Sessions', 'Engage in final practice sessions and review all sections to ensure readiness.');

INSERT INTO Lesson (course_id, lesson_number, topics_covered, outcome)
VALUES (21, 'Lesson 21 - Lesson 25', 'Exam Techniques\nFinal Tips', 'Learn effective exam techniques and receive final tips for the IELTS test.');


SELECT * FROM Enrollment e ;
