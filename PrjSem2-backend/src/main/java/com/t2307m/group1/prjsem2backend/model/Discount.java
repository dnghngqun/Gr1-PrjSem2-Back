package com.t2307m.group1.prjsem2backend.model;

import jakarta.persistence.*;

@Entity
@Table(name = "Discount")
public class Discount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String code;
    private Integer value;
    private Integer remaining;

    public Discount() {
    }

    public Discount(String code, Integer value, Integer remaining) {
        this.code = code;
        this.value = value;
        this.remaining = remaining;
    }

    public String getCode() {
        return code;
    }

    public Integer getRemaining() {
        return remaining;
    }

    public void setRemaining(Integer remaining) {
        this.remaining = remaining;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
