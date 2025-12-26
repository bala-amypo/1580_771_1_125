package com.example.demo.controller;

import com.example.demo.entity.ProfitCalculationRecord;
import com.example.demo.service.impl.ProfitCalculationServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/profit")
public class ProfitCalculationController {

    private final ProfitCalculationServiceImpl profitService;

    public ProfitCalculationController(ProfitCalculationServiceImpl profitService) {
        this.profitService = profitService;
    }

    @PostMapping("/calculate/{menuItemId}")
    public ResponseEntity<ProfitCalculationRecord> calculate(@PathVariable Long menuItemId) {
        return ResponseEntity.ok(profitService.calculateProfit(menuItemId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProfitCalculationRecord> getById(@PathVariable Long id) {
        return ResponseEntity.ok(profitService.getCalculationById(id));
    }

    @GetMapping("/menu-item/{menuItemId}")
    public ResponseEntity<List<ProfitCalculationRecord>> getByMenuItem(
            @PathVariable Long menuItemId) {
        return ResponseEntity.ok(
                profitService.getCalculationsForMenuItem(menuItemId)
        );
    }

    @GetMapping
    public ResponseEntity<List<ProfitCalculationRecord>> getAll() {
        return ResponseEntity.ok(profitService.getAllCalculations());
    }
}
