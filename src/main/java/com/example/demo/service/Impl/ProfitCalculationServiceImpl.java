// package com.example.demo.service.impl;

// import com.example.demo.entity.*;
// import com.example.demo.exception.BadRequestException;
// import com.example.demo.exception.ResourceNotFoundException;
// import com.example.demo.repository.*;
// import org.springframework.stereotype.Service;
// import java.math.BigDecimal;
// import java.util.List;
// @Service
// public class ProfitCalculationServiceImpl {

//     private final MenuItemRepository menuItemRepository;
//     private final RecipeIngredientRepository recipeIngredientRepository;
//     private final IngredientRepository ingredientRepository;
//     private final ProfitCalculationRecordRepository recordRepository;

//     public ProfitCalculationServiceImpl(
//             MenuItemRepository menuItemRepository,
//             RecipeIngredientRepository recipeIngredientRepository,
//             IngredientRepository ingredientRepository,
//             ProfitCalculationRecordRepository recordRepository) {

//         this.menuItemRepository = menuItemRepository;
//         this.recipeIngredientRepository = recipeIngredientRepository;
//         this.ingredientRepository = ingredientRepository;
//         this.recordRepository = recordRepository;
//     }

//     public ProfitCalculationRecord calculateProfit(Long menuItemId) {
//         MenuItem item = menuItemRepository.findById(menuItemId)
//                 .orElseThrow(() -> new ResourceNotFoundException("Menu item not found"));

//         List<RecipeIngredient> list =
//                 recipeIngredientRepository.findByMenuItemId(menuItemId);

//         if (list.isEmpty()) {
//             throw new BadRequestException("No ingredients");
//         }

//         BigDecimal totalCost = BigDecimal.ZERO;

//         for (RecipeIngredient ri : list) {
//             Ingredient ing = ri.getIngredient();
//             totalCost = totalCost.add(
//                     ing.getCostPerUnit().multiply(BigDecimal.valueOf(ri.getQuantity()))
//             );
//         }

//         BigDecimal profit =
//                 item.getSellingPrice().subtract(totalCost);

//         ProfitCalculationRecord record = new ProfitCalculationRecord();
//         record.setMenuItem(item);
//         record.setTotalCost(totalCost);
//         record.setProfitMargin(profit);

//         return recordRepository.save(record);
//     }

//     public ProfitCalculationRecord getCalculationById(Long id) {
//         return recordRepository.findById(id)
//                 .orElseThrow(() -> new ResourceNotFoundException("Calculation not found"));
//     }

//     public List<ProfitCalculationRecord> getCalculationsForMenuItem(Long menuItemId) {
//         return recordRepository.findByMenuItemId(menuItemId);
//     }

//     public List<ProfitCalculationRecord> getAllCalculations() {
//         return recordRepository.findAll();
//     }

//     public List<ProfitCalculationRecord> findRecordsWithMarginBetween(Double min, Double max) {
//         return recordRepository.findAll();
//     }
// }



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
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
public class ProfitCalculationServiceImpl implements ProfitCalculationService {
    
    private final MenuItemRepository menuItemRepository;
    private final RecipeIngredientRepository recipeIngredientRepository;
    private final IngredientRepository ingredientRepository;
    private final ProfitCalculationRecordRepository profitCalculationRecordRepository;
    
    @PersistenceContext
    private EntityManager entityManager;

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
            throw new BadRequestException("Cannot calculate profit for menu item without ingredients");
        }
        
        BigDecimal totalCost = BigDecimal.ZERO;
        for (RecipeIngredient ri : recipeIngredients) {
            BigDecimal ingredientCost = ri.getIngredient().getCostPerUnit()
                .multiply(BigDecimal.valueOf(ri.getQuantityRequired()));
            totalCost = totalCost.add(ingredientCost);
        }
        
        BigDecimal profit = menuItem.getSellingPrice().subtract(totalCost);
        BigDecimal profitMargin = profit.divide(menuItem.getSellingPrice(), 4, RoundingMode.HALF_UP)
            .multiply(BigDecimal.valueOf(100));
        
        ProfitCalculationRecord record = new ProfitCalculationRecord();
        record.setMenuItem(menuItem);
        record.setTotalCost(totalCost);
        record.setProfitMargin(profit);
        
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
        if (entityManager == null) {
            return List.of();
        }
        
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<ProfitCalculationRecord> cq = cb.createQuery(ProfitCalculationRecord.class);
        Root<ProfitCalculationRecord> root = cq.from(ProfitCalculationRecord.class);
        
        cq.select(root).where(
            cb.between(root.get("profitMargin"), BigDecimal.valueOf(min), BigDecimal.valueOf(max))
        );
        
        return entityManager.createQuery(cq).getResultList();
    }
}