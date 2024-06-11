CREATE DATABASE CourseManagement;
use CourseManagement;


CREATE TABLE Category(
    id INT AUTO_INCREMENT PRIMARY KEY ,
    categoryName varchar(255),
    description varchar(255)
);

CREATE TABLE Course(
    id INT AUTO_INCREMENT PRIMARY KEY ,
    name varchar(255) not null ,
    price decimal,
    categoryId INT,
    status TINYINT,
    FOREIGN KEY (categoryId) REFERENCES Category(id)
);



CREATE TABLE Instructor (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    bio TEXT, # mô tả về giáo viên
    email VARCHAR(255) NOT NULL,
    phoneNumber VARCHAR(255)
);

CREATE TABLE Account(
    id INT AUTO_INCREMENT PRIMARY KEY ,
    userName varchar(255) not null ,
    password varchar(255) not null,
    email varchar(255) not null,
    phoneNumber varchar(255) not null,
    createdAt DATE,
    updateAt DATE,
    role enum('admin', 'customer', 'staff') not null
);

CREATE TABLE Review (
    id INT AUTO_INCREMENT PRIMARY KEY,
    courseId INT NOT NULL,
    userId INT NOT NULL,
    rating INT CHECK (rating >= 1 AND rating <= 5),
    comment TEXT,
    reviewDate DATE,
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
    FOREIGN KEY (classId) REFERENCES Class(id),
    FOREIGN KEY (userId) REFERENCES Account(id)
);

-- Bảng lưu trữ thông tin thông báo
CREATE TABLE Notification (
    id INT AUTO_INCREMENT PRIMARY KEY,
    userId INT NOT NULL,
    message TEXT,
    dateSent DATE,
    status TINYINT,
    FOREIGN KEY (userId) REFERENCES Account(id)
);




CREATE TABLE Order_(
    id INT AUTO_INCREMENT PRIMARY KEY ,
    userId INT,
    totalPrice decimal,
    createAt DATE,
    updateAt DATE,
    status TINYINT,
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
    FOREIGN KEY (courseId) REFERENCES Course(id),
    FOREIGN KEY (orderId) REFERENCES Order_(id)
);

CREATE TABLE Payment(
    id INT AUTO_INCREMENT PRIMARY KEY ,
    userId int,
    paymentMethod ENUM('Visa', 'MasterCard', 'COD'),
    orderDetailId int not null ,
    amount decimal,
    paymentDate DATE,
    status tinyint, # trạng thái: paid, outstanding, cancel
    FOREIGN KEY (userId) REFERENCES Account(id),
    FOREIGN KEY (orderDetailId) REFERENCES OrderDetail(id)
);

CREATE TABLE ImageCourse(
    id INT AUTO_INCREMENT PRIMARY KEY ,
    courseId INT,
    path varchar(255),
    updateAt DATE,
    FOREIGN KEY (courseId) REFERENCES Course(id)
);

SELECT * FROM Account;

