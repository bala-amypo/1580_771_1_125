package com.example.demo.service.impl;

import com.example.demo.entity.Ingredient;
import com.example.demo.entity.MenuItem;
import com.example.demo.entity.RecipeIngredient;
import com.example.demo.repository.IngredientRepository;
import com.example.demo.repository.MenuItemRepository;
import com.example.demo.repository.RecipeIngredientRepository;
import com.example.demo.service.RecipeIngredientService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecipeIngredientServiceImpl implements RecipeIngredientService {

    private final RecipeIngredientRepository recipeIngredientRepository;
    private final IngredientRepository ingredientRepository;
    private final MenuItemRepository menuItemRepository;

    public RecipeIngredientServiceImpl(
            RecipeIngredientRepository recipeIngredientRepository,
            IngredientRepository ingredientRepository,
            MenuItemRepository menuItemRepository) {
        this.recipeIngredientRepository = recipeIngredientRepository;
        this.ingredientRepository = ingredientRepository;
        this.menuItemRepository = menuItemRepository;
    }

    @Override
    public RecipeIngredient addIngredientToMenuItem(RecipeIngredient recipeIngredient) {

        if (recipeIngredient.getQuantityRequired() <= 0) {
            throw new RuntimeException("Quantity must be greater than zero");
        }

        Ingredient ingredient = ingredientRepository.findById(
                recipeIngredient.getIngredient().getId())
                .orElseThrow(() -> new RuntimeException("Ingredient not found"));

        MenuItem menuItem = menuItemRepository.findById(
                recipeIngredient.getMenuItem().getId())
                .orElseThrow(() -> new RuntimeException("Menu item not found"));

        recipeIngredient.setIngredient(ingredient);
        recipeIngredient.setMenuItem(menuItem);

        return recipeIngredientRepository.save(recipeIngredient);
    }

    @Override
    public RecipeIngredient updateRecipeIngredient(Long id, Double quantity) {

        if (quantity <= 0) {
            throw new RuntimeException("Quantity must be greater than zero");
        }

        RecipeIngredient existing = recipeIngredientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Recipe ingredient not found"));

        existing.setQuantityRequired(quantity);
        return recipeIngredientRepository.save(existing);
    }

    @Override
    public List<RecipeIngredient> getIngredientsByMenuItem(Long menuItemId) {
        return recipeIngredientRepository.findByMenuItemId(menuItemId);
    }

    @Override
    public void removeIngredientFromRecipe(Long id) {
        RecipeIngredient existing = recipeIngredientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Recipe ingredient not found"));

        recipeIngredientRepository.delete(existing);
    }

    @Override
    public Double getTotalQuantityOfIngredient(Long ingredientId) {
        Double total = recipeIngredientRepository.getTotalQuantityByIngredientId(ingredientId);
        return total == null ? 0.0 : total;
    }
}
