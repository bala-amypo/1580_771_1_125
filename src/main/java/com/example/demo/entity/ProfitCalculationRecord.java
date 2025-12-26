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

    @ManyToOne(optional = false)
    @JoinColumn(name = "menu_item_id")
    private MenuItem menuItem;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal totalCost;

    @Column(nullable = false)
    private Double profitMargin;

    @Column(nullable = false, updatable = false)
    private LocalDateTime calculatedAt;

    @PrePersist
    void onCalculate() {
        this.calculatedAt = LocalDateTime.now();
    }

    // Getters & Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public MenuItem getMenuItem() { return menuItem; }
    public void setMenuItem(MenuItem menuItem) { this.menuItem = menuItem; }

    public BigDecimal getTotalCost() { return totalCost; }
    public void setTotalCost(BigDecimal totalCost) { this.totalCost = totalCost; }

    public Double getProfitMargin() { return profitMargin; }
    public void setProfitMargin(Double profitMargin) { this.profitMargin = profitMargin; }

    public LocalDateTime getCalculatedAt() { return calculatedAt; }
}
