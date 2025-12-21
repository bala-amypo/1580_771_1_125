package com.example.demo.controller;

import com.example.demo.entity.ProfitCalculationRecord;
import com.example.demo.service.ProfitCalculationService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/profit")
public class ProfitCalculationController {

    private final ProfitCalculationService profitCalculationService;

    public ProfitCalculationController(
            ProfitCalculationService profitCalculationService) {
        this.profitCalculationService = profitCalculationService;
    }

    // CALCULATE PROFIT
    @PostMapping("/calculate/{menuItemId}")
    public ProfitCalculationRecord calculate(
            @PathVariable Long menuItemId) {
        return profitCalculationService.calculateProfit(menuItemId);
    }

    // GET CALCULATION BY ID
    @GetMapping("/{id}")
    public ProfitCalculationRecord getById(@PathVariable Long id) {
        return profitCalculationService.getCalculationById(id);
    }

    // GET ALL CALCULATIONS FOR MENU ITEM
    @GetMapping("/menu-item/{menuItemId}")
    public List<ProfitCalculationRecord> getByMenuItem(
            @PathVariable Long menuItemId) {
        return profitCalculationService.getCalculationsForMenuItem(menuItemId);
    }

    // GET ALL CALCULATIONS
    @GetMapping
    public List<ProfitCalculationRecord> getAll() {
        return profitCalculationService.getAllCalculations();
    }
}
