package com.example.demo.controller;

import com.example.demo.entity.ProfitCalculationRecord;
import com.example.demo.service.ProfitCalculationService;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;


import java.util.List;

@RestController
@RequestMapping("/api/profit")
public class ProfitCalculationController {

    private final ProfitCalculationService profitCalculationService;

    public ProfitCalculationController(
            ProfitCalculationService profitCalculationService) {
        this.profitCalculationService = profitCalculationService;
    }

    @PostMapping("/calculate/{menuItemId}")
    public ResponseEntity<ProfitCalculationRecord> calculate(
            @PathVariable Long menuItemId) {
        return ResponseEntity.status(201).body(profitCalculationService.calculateProfit(menuItemId));
    }

    @GetMapping("/{id}")
    public ProfitCalculationRecord getById(@PathVariable Long id) {
        return profitCalculationService.getCalculationById(id);
    }

    @GetMapping("/menu-item/{menuItemId}")
    public List<ProfitCalculationRecord> getByMenuItem(
            @PathVariable Long menuItemId) {
        return profitCalculationService.getCalculationsForMenuItem(menuItemId);
    }

    @GetMapping
    public List<ProfitCalculationRecord> getAll() {
        return profitCalculationService.getAllCalculations();
    }
}
