// package com.example.demo.entity;

// import jakarta.persistence.*;
// import java.math.BigDecimal;
// import java.sql.Timestamp;

// @Entity
// @Table(name = "profit_calculation_records")
// public class ProfitCalculationRecord {

//     @Id
//     @GeneratedValue(strategy = GenerationType.IDENTITY)
//     private Long id;

//     @ManyToOne
//     @JoinColumn(name = "menuitem_id")
//     private MenuItem menuItem;

//     private BigDecimal totalCost;

//     private BigDecimal profitMargin;

//     private Timestamp calculatedAt;

//     @PrePersist
//     protected void onCreate() {
//         calculatedAt = new Timestamp(System.currentTimeMillis());
//     }

//     public Long getId() {
//         return id;
//     }

//     public MenuItem getMenuItem() {
//         return menuItem;
//     }

//     public BigDecimal getTotalCost() {
//         return totalCost;
//     }

//     public BigDecimal getProfitMargin() {
//         return profitMargin;
//     }

//     public Timestamp getCalculatedAt() {
//         return calculatedAt;
//     }

//     public void setId(Long id) {
//         this.id = id;
//     }

//     public void setMenuItem(MenuItem menuItem) {
//         this.menuItem = menuItem;
//     }

//     public void setTotalCost(BigDecimal totalCost) {
//         this.totalCost = totalCost;
//     }

//     public void setProfitMargin(BigDecimal profitMargin) {
//         this.profitMargin = profitMargin;
//     }
// }


package com.example.demo.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "profit_calculation_records")
public class ProfitCalculationRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_item_id", nullable = false)
    private MenuItem menuItem;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal totalCost;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal profitMargin;

    @Column(nullable = false)
    private LocalDateTime calculatedAt;

    @PrePersist
    protected void onCreate() {
        calculatedAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public MenuItem getMenuItem() {
        return menuItem;
    }

    public void setMenuItem(MenuItem menuItem) {
        this.menuItem = menuItem;
    }

    public BigDecimal getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(BigDecimal totalCost) {
        this.totalCost = totalCost;
    }

    public BigDecimal getProfitMargin() {
        return profitMargin;
    }

    public void setProfitMargin(BigDecimal profitMargin) {
        this.profitMargin = profitMargin;
    }

    public LocalDateTime getCalculatedAt() {
        return calculatedAt;
    }

    public void setCalculatedAt(LocalDateTime calculatedAt) {
        this.calculatedAt = calculatedAt;
    }
}