package com.example.demo.service.impl;

import com.example.demo.entity.MenuItem;
import com.example.demo.entity.ProfitCalculationRecord;
import com.example.demo.entity.RecipeIngredient;
import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.IngredientRepository;
import com.example.demo.repository.MenuItemRepository;
import com.example.demo.repository.ProfitCalculationRecordRepository;
import com.example.demo.repository.RecipeIngredientRepository;
import com.example.demo.service.ProfitCalculationService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ProfitCalculationServiceImpl implements ProfitCalculationService {

    private final MenuItemRepository menuItemRepository;
    private final RecipeIngredientRepository recipeIngredientRepository;
    private final IngredientRepository ingredientRepository;
    private final ProfitCalculationRecordRepository profitCalculationRecordRepository;

    public ProfitCalculationServiceImpl(MenuItemRepository menuItemRepository,
                                        RecipeIngredientRepository recipeIngredientRepository,
                                        IngredientRepository ingredientRepository,
                                        ProfitCalculationRecordRepository profitCalculationRecordRepository) {
        this.menuItemRepository = menuItemRepository;
        this.recipeIngredientRepository = recipeIngredientRepository;
        this.ingredientRepository = ingredientRepository;
        this.profitCalculationRecordRepository = profitCalculationRecordRepository;
    }

    @Override
    public ProfitCalculationRecord calculateProfit(Long menuItemId) {
        MenuItem menuItem = menuItemRepository.findById(menuItemId)
                .orElseThrow(() -> new ResourceNotFoundException("Menu item not found"));

        List<RecipeIngredient> recipeIngredients = recipeIngredientRepository.findByMenuItemId(menuItemId);
        if (recipeIngredients.isEmpty()) {
            throw new BadRequestException("Cannot calculate profit: menu item has no recipe ingredients");
        }

        BigDecimal totalCost = BigDecimal.ZERO;
        for (RecipeIngredient ri : recipeIngredients) {
            BigDecimal cost = ri.getIngredient().getCostPerUnit().multiply(BigDecimal.valueOf(ri.getQuantityRequired()));
            totalCost = totalCost.add(cost);
        }

        BigDecimal profitMargin = menuItem.getSellingPrice().subtract(totalCost);

        ProfitCalculationRecord record = new ProfitCalculationRecord();
        record.setMenuItem(menuItem);
        record.setTotalCost(totalCost);
        record.setProfitMargin(profitMargin);
        record.setCalculatedAt(LocalDateTime.now());

        return profitCalculationRecordRepository.save(record);
    }

    @Override
    public ProfitCalculationRecord getCalculationById(Long id) {
        return profitCalculationRecordRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Profit calculation record not found"));
    }

    @Override
    public List<ProfitCalculationRecord> getCalculationsForMenuItem(Long menuItemId) {
        return profitCalculationRecordRepository.findByMenuItemId(menuItemId);
    }

    @Override
    public List<ProfitCalculationRecord> getAllCalculations() {
        return profitCalculationRecordRepository.findAll();
    }

    @Override
    public List<ProfitCalculationRecord> findRecordsWithMarginBetween(Double min, Double max) {
        return profitCalculationRecordRepository.findAll().stream()
                .filter(r -> r.getProfitMargin().doubleValue() >= min && r.getProfitMargin().doubleValue() <= max)
                .toList();
    }
}
