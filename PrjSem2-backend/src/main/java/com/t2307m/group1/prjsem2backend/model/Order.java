package com.t2307m.group1.prjsem2backend.model;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;




}
