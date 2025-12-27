// package com.example.demo.entity;

// import jakarta.persistence.*;
// import java.math.BigDecimal;
// import java.sql.Timestamp;

// @Entity
// @Table(name = "ingredients")
// public class Ingredient {

//     @Id
//     @GeneratedValue(strategy = GenerationType.IDENTITY)
//     private Long id;

//     @Column(unique = true, nullable = false)
//     private String name;

//     private String unit;

//     @Column(nullable = false)
//     private BigDecimal costPerUnit;

//     private Boolean active = true;

//     private Timestamp createdAt;
//     private Timestamp updatedAt;

//     @PrePersist
//     protected void onCreate() {
//         createdAt = new Timestamp(System.currentTimeMillis());
//         updatedAt = createdAt;
//     }

//     @PreUpdate
//     protected void onUpdate() {
//         updatedAt = new Timestamp(System.currentTimeMillis());
//     }

//     public Long getId() {
//         return id;
//     }

//     public String getName() {
//         return name;
//     }

//     public String getUnit() {
//         return unit;
//     }

//     public BigDecimal getCostPerUnit() {
//         return costPerUnit;
//     }

//     public Boolean getActive() {
//         return active;
//     }

//     public Timestamp getCreatedAt() {
//         return createdAt;
//     }

//     public Timestamp getUpdatedAt() {
//         return updatedAt;
//     }

//     public void setId(Long id) {
//         this.id = id;
//     }

//     public void setName(String name) {
//         this.name = name;
//     }

//     public void setUnit(String unit) {
//         this.unit = unit;
//     }

//     public void setCostPerUnit(BigDecimal costPerUnit) {
//         this.costPerUnit = costPerUnit;
//     }

//     public void setActive(Boolean active) {
//         this.active = active;
//     }
// }
