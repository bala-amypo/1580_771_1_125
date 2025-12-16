package com.example.demo.entity;

import java.math.BigDecimal;
import java.sql.Timestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Fields {
    private long id;
    private String name;
    private String unit;
    private BigDecimal costPerUnit;
    private boolean active;
    private Timestamp createdAt;
    private Timestamp updatedAt;

    public Fields(){}

    public Fields(boolean active, BigDecimal costPerUnit, Timestamp createdAt, long id, String name, String unit, Timestamp updatedAt) {
        this.active = active;
        this.costPerUnit = costPerUnit;
        this.createdAt = createdAt;
        this.id = id;
        this.name = name;
        this.unit = unit;
        this.updatedAt = updatedAt;
    }

}