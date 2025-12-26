package com.example.demo.controller;

import com.example.demo.entity.ProfitCalculationRecord;
import com.example.demo.service.ProfitCalculationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/profit")
public class ProfitCalculationController {

    private final ProfitCalculationService profitCalculationService;

    public ProfitCalculationController(ProfitCalculationService profitCalculationService) {
        this.profitCalculationService = profitCalculationService;
    }

    @PostMapping("/calculate/{menuItemId}")
    public ResponseEntity<ProfitCalculationRecord> calculateProfit(@PathVariable Long menuItemId) {
        return new ResponseEntity<>(profitCalculationService.calculateProfit(menuItemId), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProfitCalculationRecord> getCalculationById(@PathVariable Long id) {
        return ResponseEntity.ok(profitCalculationService.getCalculationById(id));
    }

    @GetMapping("/menu-item/{menuItemId}")
    public ResponseEntity<List<ProfitCalculationRecord>> getCalculationsForMenuItem(@PathVariable Long menuItemId) {
        return ResponseEntity.ok(profitCalculationService.getCalculationsForMenuItem(menuItemId));
    }

    @GetMapping
    public ResponseEntity<List<ProfitCalculationRecord>> getAllCalculations() {
        return ResponseEntity.ok(profitCalculationService.getAllCalculations());
    }
}
