package com.example.demo.entity;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.DecimalMin;

@Entity
@Table(name = "ingredients", uniqueConstraints = @UniqueConstraint(columnNames = "name"))
public class Ingredient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private String unit;

    @DecimalMin(value = "0.0", inclusive = false)
    @Column(nullable = false)
    private BigDecimal costPerUnit;

    @Column(nullable = false)
    private Boolean active = true;

    private Timestamp createdAt;

    private Timestamp updatedAt;

    @PrePersist
    void onCreate() {
        Timestamp now = Timestamp.from(Instant.now());
        createdAt = now;
        updatedAt = now;
    }

    @PreUpdate
    void onUpdate() {
        updatedAt = Timestamp.from(Instant.now());
    }

public Long getId() { return id; }
public String getName() { return name; }
public void setName(String name) { this.name = name; }
public String getUnit() { return unit; }
public void setUnit(String unit) { this.unit = unit; }
public BigDecimal getCostPerUnit() { return costPerUnit; }
public void setCostPerUnit(BigDecimal costPerUnit) { this.costPerUnit = costPerUnit; }
public Boolean getActive() { return active; }
public void setActive(Boolean active) { this.active = active; }
public Timestamp getCreatedAt() { return createdAt; }
public Timestamp getUpdatedAt() { return updatedAt; }

}
