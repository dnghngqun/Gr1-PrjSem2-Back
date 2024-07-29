package com.t2307m.group1.prjsem2backend.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import org.antlr.v4.runtime.misc.NotNull;

import java.sql.Timestamp;

@Entity
public class OrderDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    //  chỉ định mối quan hệ nhiều-một với thực thể Course và sử dụng tải chậm (lazy loading).
    // optional = false nghĩa là thuộc tính này không thể null
//    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    //chỉ định rằng thuộc tính này chỉ được sử dụng khi ghi (serialization), không khi đọc (deserialization).
    @JoinColumn(name = "courseId",nullable = false)
    // chỉ định rằng cột courseId trong bảng OrderDetail sẽ lưu trữ khóa ngoại tham chiếu đến Course.
    private Course course;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    //chỉ định mối quan hệ nhiều-một với thực thể Order và sử dụng tải chậm. optional = false nghĩa là thuộc tính này không thể null.
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    //chỉ định rằng thuộc tính này chỉ được sử dụng khi ghi, không khi đọc.
    @JoinColumn(name = "orderId", nullable = false)
    //chỉ định rằng cột orderId trong bảng OrderDetail sẽ lưu trữ khóa ngoại tham chiếu đến Order.
    private Order order;


    private Double discount;
    private Double totalAmount;
    @Transient //giá trị này không được ánh xạ vào database
    private Timestamp createdAt;
    @Transient
    private Timestamp updateAt;
    private int status;

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

    public OrderDetail() {
    }

    public OrderDetail(Order order, Double discount, Double totalAmount, Course course, Integer status) {
        this.order = order;
        this.discount = discount;
        this.totalAmount = totalAmount;
        this.course = course;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public void setStatus(Integer status) {this.status = status;}

    public Integer getStatus() {return status;}

}
