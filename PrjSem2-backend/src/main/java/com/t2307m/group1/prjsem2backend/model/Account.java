package com.t2307m.group1.prjsem2backend.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.util.Date;

@Entity
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String userName;
    private String email;
    private String password;
    private String phoneNumber;// this field require regex
    private String role = "customer"; //default customer, if admin or staff, ghi de
    private Date createdAt;// khoi tao dung 1 lan
    private Date updateAt;

}
