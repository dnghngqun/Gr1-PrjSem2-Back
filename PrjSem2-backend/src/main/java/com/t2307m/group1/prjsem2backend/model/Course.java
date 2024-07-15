package com.t2307m.group1.prjsem2backend.model;

import jakarta.persistence.*;

import java.sql.Timestamp;

@Entity
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private Double price;
    private String imgLink;
    @Column(name = "classify")
    private String classify;
    private Integer status;
    @Transient //giá trị này không được ánh xạ vào database
    private Timestamp createdAt;
    @Transient
    private Timestamp updateAt;

    public Timestamp getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(Timestamp updateAt) {
        this.updateAt = updateAt;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Course() {
    }

    public String getImgLink() {
        return imgLink;
    }

    public void setImgLink(String imgLink) {
        this.imgLink = imgLink;
    }

    public Course(String name, Double price, String imgLink, String classify, Integer status) {
        this.name = name;
        this.price = price;
        this.imgLink = imgLink;
        this.classify = classify;
        this.status = status;
    }

    public String getClassify() {
        return classify;
    }

    public void setClassify(String classify) {
        this.classify = classify;
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

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getStatus() {
        return status;
    }



}
