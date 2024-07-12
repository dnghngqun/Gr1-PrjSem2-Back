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



CREATE TABLE Category(
    id INT AUTO_INCREMENT PRIMARY KEY ,
    categoryName varchar(255),
    courseId int,
    description varchar(255),
    createdAt Timestamp DEFAULT CURRENT_TIMESTAMP,
    updateAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE  CURRENT_TIMESTAMP,
    FOREIGN KEY (courseId) REFERENCES Course(id)
);


CREATE TABLE Instructor (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    gender varchar(255) not null ,
    bio TEXT, # mô tả về giáo viên
    email VARCHAR(255) NOT NULL unique ,
    phoneNumber VARCHAR(255) unique ,
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
    createdAt Timestamp DEFAULT CURRENT_TIMESTAMP,
    updateAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    role enum('admin', 'customer', 'staff') not null
);

Create Table ImageAccount(
	id INT AUTO_INCREMENT PRIMARY KEY,
	userId int,
	imageLink varchar(255),
	createdAt Timestamp DEFAULT CURRENT_TIMESTAMP,
    updateAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE  CURRENT_TIMESTAMP,
    FOREIGN KEY (userId) REFERENCES Account(id)
);


CREATE TABLE Review (
    id INT AUTO_INCREMENT PRIMARY KEY,
    courseId INT NOT NULL,
    userId INT NOT NULL,
    rating INT CHECK (rating >= 1 AND rating <= 5),
    comment TEXT,
    reviewDate TIMESTAMP,
    createdAt Timestamp DEFAULT CURRENT_TIMESTAMP,
    updateAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (courseId) REFERENCES Course(id),
    FOREIGN KEY (userId) REFERENCES Account(id)
);

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
    enrollmentDate DATE,
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



SELECT * FROM Account;

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
INSERT INTO Account(username, password, fullname, birthday, email, phonenumber, role) values ('hongquan', 123456, 'Dang Hong Quan', '1999-01-01','quandhth2304004@fpt.edu.vn','0383240511', 'customer');
INSERT INTO Account(username, password, fullname, birthday, email, phonenumber, role) values ('phamhoang', 123456, 'Pham Nhat Hoang', '1999-01-01','hoangpnth2304021@fpt.edu.vn','0915298826', 'customer');
INSERT INTO Account(username, password, fullname, birthday, email, phonenumber, role) values ('dothao', 123456, 'Do Thi Thao', '1999-01-01','thaodtth2304010@fpt.edu.vn','0348279942', 'customer');
SELECT * FROM  Account;

INSERT INTO Course(name, price,imgLink,classify, status) values ('Toeic Basic', 50,"https://i.imgur.com/ODfI54z.png","TOEIC2", 0);
INSERT INTO Course(name, price,imgLink,classify, status) values ('Toeic Pre', 100,"https://i.imgur.com/8dpJvLe.png","TOEIC2", 0);
INSERT INTO Course(name, price,imgLink,classify, status) values ('Toeic A', 120,"https://i.imgur.com/OdYAIsW.png","TOEIC2", 0);
INSERT INTO Course(name, price,imgLink,classify, status) values ('Ielts Basic', 50,"https://i.imgur.com/CEUHoyL.png","IELTS", 0);
INSERT INTO Course(name, price,imgLink,classify, status) values ('Ielts Pre', 100,"https://i.imgur.com/22AovQJ.png","IELTS", 0);
INSERT INTO Course(name, price,imgLink,classify, status) values ('Toeic A', 120,"https://i.imgur.com/OdYAIsW.png","TOEIC2", 0);
INSERT INTO Course(name, price,imgLink,classify, status) values ('Ielts Basic', 50,"https://i.imgur.com/CEUHoyL.png","IELTS", 0);
INSERT INTO Course(name, price,imgLink,classify, status) values ('Ielts Pre', 100,"https://i.imgur.com/22AovQJ.png","IELTS", 0);
INSERT INTO Course(name, price,imgLink,classify, status) values ('Toeic A', 120,"https://i.imgur.com/OdYAIsW.png","TOEIC2", 0);
INSERT INTO Course(name, price,imgLink,classify, status) values ('Ielts Basic', 50,"https://i.imgur.com/CEUHoyL.png","IELTS", 0);
INSERT INTO Course(name, price,imgLink,classify, status) values ('Ielts Pre', 100,"https://i.imgur.com/22AovQJ.png","IELTS", 0);
INSERT INTO Course(name, price,imgLink,classify, status) values ('Toeic A', 120,"https://i.imgur.com/OdYAIsW.png","TOEIC2", 0);
INSERT INTO Course(name, price,imgLink,classify, status) values ('Ielts Basic', 50,"https://i.imgur.com/CEUHoyL.png","IELTS", 0);
INSERT INTO Course(name, price,imgLink,classify, status) values ('Ielts Pre', 100,"https://i.imgur.com/22AovQJ.png","IELTS", 0);

SELECT * FROM Course;

INSERT INTO Order_(userId, status) values(1,0);
INSERT INTO Order_(userId, status) values(2,0);
INSERT INTO Order_(userId, status) values(3,0);

SELECT * FROM Order_;

INSERT INTO Instructor(name, bio, email, gender, phoneNumber) values ('Nguyễn Cẩm Tú', '900 TOEIC Người giáo viên khơi dậy niềm đam mê với việc học Tiếng Anh trong các em. Sở thích: Nghe nhạc và xem phim tiếng Anh','instructor1@gmail.com', 'female', '0123456765');
INSERT INTO Instructor(name, bio, email, gender, phoneNumber) values ('Nguyễn Khánh Linh', '960 TOEIC Cô giáo năng động và tràn đầy nhiệt huyết với nghề Cô có phong cách giảng dạy vô cùng cuốn hút','instructor2@gmail.com', 'female', '0126456765');
INSERT INTO Instructor(name, bio, email, gender, phoneNumber) values ('Lê Hoài Anh', '930 TOEIC Sinh và lớn lên ở thành phố biển Hạ Long ','instructor3@gmail.com', 'male', '0153456765');

SELECT * FROM Instructor;

INSERT INTO Class(courseId, instructorId, location, startDate, endDate, status) values (1,1,'P301 14h30-16h', '2024-08-11','2024-10-05', 0);
INSERT INTO Class(courseId, instructorId, location, startDate, endDate, status) values (2,1,'P303 17h-18h30', '2024-08-11','2024-10-05', 0);
INSERT INTO Class(courseId, instructorId, location, startDate, endDate, status) values (2,2,'P301 17h-18h30', '2024-08-12','2024-10-07', 0);
INSERT INTO Class(courseId, instructorId, location, startDate, endDate, status) values (3,2,'P401 9h-10h30', '2024-07-11','2024-10-05', 0);
INSERT INTO Class(courseId, instructorId, location, startDate, endDate, status) values (2,3,'P201 17h-18h30', '2024-08-05','2024-10-01', 0);
INSERT INTO Class(courseId, instructorId, location, startDate, endDate, status) values (3,3,'P301 17h-18h30', '2024-06-16','2024-09-05', 0);

select * FROM Class;

SELECT  * FROM Payment ;