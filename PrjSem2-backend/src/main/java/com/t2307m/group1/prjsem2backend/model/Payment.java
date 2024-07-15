package com.t2307m.group1.prjsem2backend.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import org.antlr.v4.runtime.misc.NotNull;

import java.sql.Timestamp;
@Entity
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "paymentId",nullable = false)
    private String paymentId; //giá trị này được paypal trả về

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @JoinColumn(name = "userId", nullable = false)
    private Account account;


    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @JoinColumn(name = "orderDetailId", nullable = false)
    private OrderDetail orderDetail;

    @Column(name = "paymentMethod")
    private String paymentMethod;
    @Column(name = "amount")
    private Double amount;
    @Column(name = "paymentDate")
    private Timestamp paymentDate;
    @Column(name = "status")
    private Integer status;
    @Transient //giá trị này không được ánh xạ vào database
    private Timestamp createdAt;
    @Transient
    private Timestamp updateAt;

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

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

    public Payment() {
    }

    public Payment(String paymentId, Account account, OrderDetail orderDetail, String paymentMethod, Double amount, Timestamp paymentDate, Integer status) {
        this.paymentId = paymentId;
        this.account = account;
        this.orderDetail = orderDetail;
        this.paymentMethod = paymentMethod;
        this.amount = amount;
        this.paymentDate = paymentDate;
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

    public OrderDetail getOrderDetail() {
        return orderDetail;
    }

    public void setOrderDetail(OrderDetail orderDetail) {
        this.orderDetail = orderDetail;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Timestamp getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Timestamp paymentDate) {
        this.paymentDate = paymentDate;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}