package com.t2307m.group1.prjsem2backend.model;

import jakarta.persistence.*;

@Entity
@Table(name = "ImageAccount")
public class ImageAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "imageLink")
    private String imageLink;

    @OneToOne(fetch = FetchType.EAGER) // dữ liệu luôn được lấy khi gọi đến
    private Account account;

    public ImageAccount() {
    }

    public ImageAccount(String imageLink, Account account) {
        this.imageLink = imageLink;
        this.account = account;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
}
