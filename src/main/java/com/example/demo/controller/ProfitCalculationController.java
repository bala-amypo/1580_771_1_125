package com.example.demo.controller;

import com.example.demo.entity.ProfitCalculationRecord;
import com.example.demo.service.ProfitCalculationService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/profits")
public class ProfitController {

    private final ProfitCalculationService service;

    public ProfitController(ProfitCalculationService service) {
        this.service = service;
    }

    @PostMapping("/calculate/{menuItemId}")
    public ProfitCalculationRecord calculate(@PathVariable Long menuItemId) {
        return service.calculate(menuItemId);
    }
}
