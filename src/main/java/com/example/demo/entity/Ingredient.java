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

package com.example.demo.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "ingredients")
public class Ingredient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;

    @Column(nullable = false)
    private String unit;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal costPerUnit;

    @Column(nullable = false)
    private Boolean active = true;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}