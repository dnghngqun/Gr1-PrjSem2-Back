package com.t2307m.group1.prjsem2backend.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import org.antlr.v4.runtime.misc.NotNull;

import java.sql.Timestamp;

@Entity
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Account account;
    private String message;
    private Timestamp dateSent;
    private int status;

    public Notification() {
    }

    public Notification(Account account, String message, Timestamp dateSent, int status) {
        this.account = account;
        this.message = message;
        this.dateSent = dateSent;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Timestamp getDateSent() {
        return dateSent;
    }

    public void setDateSent(Timestamp dateSent) {
        this.dateSent = dateSent;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
