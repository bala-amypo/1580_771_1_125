package com.example.demo.service;

import java.util.List;
import com.example.demo.entity.Ingredient;

public interface IngredientService {
    Ingredient createIngredient(Ingredient ingredient);
    Ingredient updateIngredient(Long id, Ingredient ingredient);
    Ingredient getIngredientById(Long id);
    List<Ingredient> getAllIngredients();
    void deactivateIngredient(Long id);
}
