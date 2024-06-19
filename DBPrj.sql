CREATE DATABASE CourseManagement;
use CourseManagement;


CREATE TABLE Course(
    id INT AUTO_INCREMENT PRIMARY KEY ,
    name varchar(255) not null ,
    price decimal,
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
    bio TEXT, # mô tả về giáo viên
    email VARCHAR(255) NOT NULL,
    phoneNumber VARCHAR(255),
    createdAt Timestamp DEFAULT CURRENT_TIMESTAMP,
    updateAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE Account(
    id INT AUTO_INCREMENT PRIMARY KEY ,
    userName varchar(255) not null ,
    password varchar(255) not null,
    email varchar(255) not null,
    phoneNumber varchar(255) not null,
    createdAt Timestamp DEFAULT CURRENT_TIMESTAMP,
    updateAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    role enum('admin', 'customer', 'staff') not null
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
    quantity int ,
    totalAmount decimal,
    status tinyint,
    createdAt Timestamp DEFAULT CURRENT_TIMESTAMP,
    updateAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (courseId) REFERENCES Course(id),
    FOREIGN KEY (orderId) REFERENCES Order_(id)
);

CREATE TABLE Payment(
    id INT AUTO_INCREMENT PRIMARY KEY ,
    userId int,
    paymentMethod ENUM('Visa', 'MasterCard', 'COD'),
    orderDetailId int not null ,
    amount decimal,
    paymentDate TIMESTAMP,
    status tinyint, # trạng thái: paid, outstanding, cancel
    createdAt Timestamp DEFAULT CURRENT_TIMESTAMP,
    updateAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (userId) REFERENCES Account(id),
    FOREIGN KEY (orderDetailId) REFERENCES OrderDetail(id)
);

CREATE TABLE ImageCourse(
    id INT AUTO_INCREMENT PRIMARY KEY ,
    courseId INT,
    path varchar(255),
    createdAt Timestamp DEFAULT CURRENT_TIMESTAMP,
    updateAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (courseId) REFERENCES Course(id)
);

SELECT * FROM Account;

