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

    @Column(nullable = false)
    private BigDecimal totalCost;

    @Column(nullable = false)
    private BigDecimal profit;

    @Column(nullable = false)
    private LocalDateTime calculatedAt;

    // ✅ Default constructor
    public ProfitCalculationRecord() {}

    // ✅ Parameterized constructor
    public ProfitCalculationRecord(Long id, MenuItem menuItem,
                                   BigDecimal totalCost, BigDecimal profit,
                                   LocalDateTime calculatedAt) {
        this.id = id;
        this.menuItem = menuItem;
        this.totalCost = totalCost;
        this.profit = profit;
        this.calculatedAt = calculatedAt;
    }

    @PrePersist
    public void onCreate() {
        this.calculatedAt = LocalDateTime.now();
    }

    // Getters & Setters
    public Long getId() { return id; }
    public MenuItem getMenuItem() { return menuItem; }
    public BigDecimal getTotalCost() { return totalCost; }
    public BigDecimal getProfit() { return profit; }
    public LocalDateTime getCalculatedAt() { return calculatedAt; }

    public void setId(Long id) { this.id = id; }
    public void setMenuItem(MenuItem menuItem) { this.menuItem = menuItem; }
    public void setTotalCost(BigDecimal totalCost) { this.totalCost = totalCost; }
    public void setProfit(BigDecimal profit) { this.profit = profit; }
    public void setCalculatedAt(LocalDateTime calculatedAt) {
        this.calculatedAt = calculatedAt;
    }
}
