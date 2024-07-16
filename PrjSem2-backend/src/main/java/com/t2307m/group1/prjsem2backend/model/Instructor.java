package com.t2307m.group1.prjsem2backend.model;

import jakarta.persistence.*;

import java.sql.Timestamp;

@Entity
public class Instructor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String bio;
    private String email;
    private String gender;
    private String imageLink;
    private String phoneNumber;
    private String classify;
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

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Timestamp getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(Timestamp updateAt) {
        this.updateAt = updateAt;
    }

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    public String getClassify() {
        return classify;
    }

    public void setClassify(String classify) {
        this.classify = classify;
    }

    public Instructor(String name, String bio, String email, String gender, String imageLink, String phoneNumber, String classify) {
        this.name = name;
        this.bio = bio;
        this.email = email;
        this.gender = gender;
        this.imageLink = imageLink;
        this.phoneNumber = phoneNumber;
        this.classify = classify;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Instructor() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }


}
