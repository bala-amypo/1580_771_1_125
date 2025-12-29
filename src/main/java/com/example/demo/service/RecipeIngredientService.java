package com.example.demo.service;

import com.example.demo.entity.RecipeIngredient;
import java.util.List;

public interface RecipeIngredientService {
    RecipeIngredient addIngredientToMenuItem(Long menuItemId, Long ingredientId, Double quantity);
    RecipeIngredient addIngredientToMenuItem(RecipeIngredient recipeIngredient);
    RecipeIngredient updateRecipeIngredient(Long id, Double quantity);
    List<RecipeIngredient> getIngredientsByMenuItem(Long menuItemId);
    void removeIngredientFromRecipe(Long id);
    Double getTotalQuantityOfIngredient(Long ingredientId);
}