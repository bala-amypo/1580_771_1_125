package com.example.demo.service.impl;

import com.example.demo.entity.RecipeIngredient;
import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.IngredientRepository;
import com.example.demo.repository.MenuItemRepository;
import com.example.demo.repository.RecipeIngredientRepository;
import com.example.demo.service.RecipeIngredientService;

import java.util.List;

public class RecipeIngredientServiceImpl implements RecipeIngredientService {

    private final RecipeIngredientRepository recipeIngredientRepository;
    private final IngredientRepository ingredientRepository;
    private final MenuItemRepository menuItemRepository;

    // ✅ Constructor order EXACT
    public RecipeIngredientServiceImpl(
            RecipeIngredientRepository recipeIngredientRepository,
            IngredientRepository ingredientRepository,
            MenuItemRepository menuItemRepository) {

        this.recipeIngredientRepository = recipeIngredientRepository;
        this.ingredientRepository = ingredientRepository;
        this.menuItemRepository = menuItemRepository;
    }

    @Override
    public RecipeIngredient addIngredientToMenuItem(RecipeIngredient ri) {
        if (ri.getQuantity() <= 0) {
            throw new BadRequestException("Invalid quantity");
        }

        ingredientRepository.findById(ri.getIngredient().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Ingredient not found"));

        menuItemRepository.findById(ri.getMenuItem().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Menu item not found"));

        return recipeIngredientRepository.save(ri);
    }

    @Override
    public RecipeIngredient updateRecipeIngredient(Long id, Double quantity) {
        if (quantity <= 0) {
            throw new BadRequestException("Invalid quantity");
        }

        RecipeIngredient ri = recipeIngredientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Recipe ingredient not found"));

        ri.setQuantity(quantity);
        return recipeIngredientRepository.save(ri);
    }

    @Override
    public List<RecipeIngredient> getIngredientsByMenuItem(Long menuItemId) {
        return recipeIngredientRepository.findByMenuItemId(menuItemId);
    }

    @Override
    public void removeIngredientFromRecipe(Long id) {
        recipeIngredientRepository.deleteById(id);
    }

    @Override
    public Double getTotalQuantityOfIngredient(Long ingredientId) {
        return recipeIngredientRepository.getTotalQuantityByIngredientId(ingredientId);
    }
}
