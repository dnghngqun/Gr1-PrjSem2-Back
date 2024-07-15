package com.t2307m.group1.prjsem2backend.model;

import jakarta.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.sql.Date;

@Entity
public class Account implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "userName",nullable = false, unique = true)
    private String userName;
    @Column(name = "email",nullable = false, unique = true)
    private String email;
    @Column(name = "password",nullable = false)
    private String password;
    @Column(name = "phoneNumber",nullable = false, unique = true)
    private String phoneNumber;// this field require regex
    @Column(name = "fullName", nullable = false)
    private String fullName;
    @Column(name = "imageAccount")
    private String imageAccount = "https://i.imgur.com/jvskbNg.jpg";//default image account
    @Column(name = "birthday", nullable = false)
    private Date birthday;
    private String role = "customer"; //default customer, if admin or staff, ghi de

    @Transient //giá trị này không được ánh xạ vào database
    private Timestamp createdAt;
    @Transient
    private Timestamp updateAt;

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(Timestamp updateAt) {
        this.updateAt = updateAt;
    }

    public Account() {
    }


    public Account(String userName, String email, String password, String phoneNumber, String fullName, String imageAccount, Date birthday, String role) {
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.fullName = fullName;
        this.imageAccount = imageAccount;
        this.birthday = birthday;
        this.role = role;
    }

    public String getImageAccount() {
        return imageAccount;
    }

    public void setImageAccount(String imageAccount) {
        this.imageAccount = imageAccount;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
