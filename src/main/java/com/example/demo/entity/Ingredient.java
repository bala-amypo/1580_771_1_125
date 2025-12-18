package com.example.demo.entity;

import java.math.BigDecimal;
import java.sql.Timestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="ingredient")
public class Ingredient {
    @Id
    private long id;
    private String name;
    private String unit;
    private BigDecimal costPerUnit;
    private boolean active;
    private Timestamp createdAt;
    private Timestamp updatedAt;

    public Ingredient(){}

    public Ingredient(boolean active, BigDecimal costPerUnit, Timestamp createdAt, long id, String name, String unit, Timestamp updatedAt) {
        this.active = active;
        this.costPerUnit = costPerUnit;
        this.createdAt = createdAt;
        this.id = id;
        this.name = name;
        this.unit = unit;
        this.updatedAt = updatedAt;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public BigDecimal getCostPerUnit() {
        return costPerUnit;
    }

    public void setCostPerUnit(BigDecimal costPerUnit) {
        this.costPerUnit = costPerUnit;
    }

    public boolean getActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }
    
}