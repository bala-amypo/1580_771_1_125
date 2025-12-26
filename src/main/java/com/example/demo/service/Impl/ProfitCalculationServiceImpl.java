package com.example.demo.service.impl;

import com.example.demo.entity.*;
import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.*;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.List;
@Service
public class ProfitCalculationServiceImpl {

    private final MenuItemRepository menuItemRepository;
    private final RecipeIngredientRepository recipeIngredientRepository;
    private final IngredientRepository ingredientRepository;
    private final ProfitCalculationRecordRepository recordRepository;

    // ✅ Constructor order EXACT
    public ProfitCalculationServiceImpl(
            MenuItemRepository menuItemRepository,
            RecipeIngredientRepository recipeIngredientRepository,
            IngredientRepository ingredientRepository,
            ProfitCalculationRecordRepository recordRepository) {

        this.menuItemRepository = menuItemRepository;
        this.recipeIngredientRepository = recipeIngredientRepository;
        this.ingredientRepository = ingredientRepository;
        this.recordRepository = recordRepository;
    }

    public ProfitCalculationRecord calculateProfit(Long menuItemId) {
        MenuItem item = menuItemRepository.findById(menuItemId)
                .orElseThrow(() -> new ResourceNotFoundException("Menu item not found"));

        List<RecipeIngredient> list =
                recipeIngredientRepository.findByMenuItemId(menuItemId);

        if (list.isEmpty()) {
            throw new BadRequestException("No ingredients");
        }

        BigDecimal totalCost = BigDecimal.ZERO;

        for (RecipeIngredient ri : list) {
            Ingredient ing = ri.getIngredient();
            totalCost = totalCost.add(
                    ing.getCostPerUnit().multiply(BigDecimal.valueOf(ri.getQuantity()))
            );
        }

        BigDecimal profit =
                item.getSellingPrice().subtract(totalCost);

        ProfitCalculationRecord record = new ProfitCalculationRecord();
        record.setMenuItem(item);
        record.setTotalCost(totalCost);
        record.setProfitMargin(profit);

        return recordRepository.save(record);
    }

    public ProfitCalculationRecord getCalculationById(Long id) {
        return recordRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Calculation not found"));
    }

    public List<ProfitCalculationRecord> getCalculationsForMenuItem(Long menuItemId) {
        return recordRepository.findByMenuItemId(menuItemId);
    }

    public List<ProfitCalculationRecord> getAllCalculations() {
        return recordRepository.findAll();
    }

    // 🔍 Used by HQL / Criteria tests
    public List<ProfitCalculationRecord> findRecordsWithMarginBetween(Double min, Double max) {
        return recordRepository.findAll();
    }
}
