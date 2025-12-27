// package com.example.demo.controller;

// import com.example.demo.entity.ProfitCalculationRecord;
// import com.example.demo.service.impl.ProfitCalculationServiceImpl;
// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.*;

// import java.util.List;

// @RestController
// @RequestMapping("/api/profit")
// public class ProfitCalculationController {

//     private final ProfitCalculationServiceImpl profitService;

//     public ProfitCalculationController(ProfitCalculationServiceImpl profitService) {
//         this.profitService = profitService;
//     }

//     @PostMapping("/calculate/{menuItemId}")
//     public ResponseEntity<ProfitCalculationRecord> calculate(@PathVariable Long menuItemId) {
//         return ResponseEntity.ok(profitService.calculateProfit(menuItemId));
//     }

//     @GetMapping("/{id}")
//     public ResponseEntity<ProfitCalculationRecord> getById(@PathVariable Long id) {
//         return ResponseEntity.ok(profitService.getCalculationById(id));
//     }

//     @GetMapping("/menu-item/{menuItemId}")
//     public ResponseEntity<List<ProfitCalculationRecord>> getByMenuItem(
//             @PathVariable Long menuItemId) {
//         return ResponseEntity.ok(
//                 profitService.getCalculationsForMenuItem(menuItemId)
//         );
//     }

//     @GetMapping
//     public ResponseEntity<List<ProfitCalculationRecord>> getAll() {
//         return ResponseEntity.ok(profitService.getAllCalculations());
//     }
// }



package com.example.demo.controller;

import com.example.demo.entity.ProfitCalculationRecord;
import com.example.demo.service.ProfitCalculationService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/profit")
@Tag(name = "Profit Calculation", description = "Profit calculation APIs")
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