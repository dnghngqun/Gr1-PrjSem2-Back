package com.t2307m.group1.prjsem2backend.model;

import jakarta.persistence.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Entity
public class PasswordResetToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "token", nullable = false)
    private String token;

    @Column(name = "expiration_date", nullable = false)
    private LocalDateTime expirationDate;

    @OneToOne(fetch = FetchType.EAGER)//dữ liệu của mối quan hệ sẽ được nạp ngay lập tức khi entity chứa mối quan hệ đó được nạp
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;

    public PasswordResetToken() {
    }

    public PasswordResetToken(String token, Account account) {
        this.token = token;
        this.account = account;
        this.expirationDate = LocalDateTime.now().plusHours(24);//token hết hạn sau 24h
    }
    public boolean isExpired() {
        return LocalDateTime.now().isAfter(expirationDate);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public LocalDateTime getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDateTime expirationDate) {
        this.expirationDate = expirationDate;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
}
