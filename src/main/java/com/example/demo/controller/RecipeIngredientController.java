package com.example.demo.controller;

import com.example.demo.entity.RecipeIngredient;
import com.example.demo.service.RecipeIngredientService;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;


import java.util.List;

@RestController
@RequestMapping("/api/recipe-ingredients")
public class RecipeIngredientController {

    private final RecipeIngredientService recipeIngredientService;

    public RecipeIngredientController(RecipeIngredientService recipeIngredientService) {
        this.recipeIngredientService = recipeIngredientService;
    }

    @PostMapping
    public ResponseEntity<RecipeIngredient> add(@RequestBody RecipeIngredient recipeIngredient) {
        return ResponseEntity.status(201).body(recipeIngredientService.addIngredientToMenuItem(recipeIngredient));
    }

    @PutMapping("/{id}")
    public RecipeIngredient updateQuantity(
            @PathVariable Long id,
            @RequestParam Double quantity) {
        return recipeIngredientService.updateRecipeIngredient(id, quantity);
    }

    @GetMapping("/menu-item/{menuItemId}")
    public List<RecipeIngredient> getByMenuItem(@PathVariable Long menuItemId) {
        return recipeIngredientService.getIngredientsByMenuItem(menuItemId);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        recipeIngredientService.removeIngredientFromRecipe(id);
        return "Recipe ingredient removed successfully";
    }

    @GetMapping("/ingredient/{ingredientId}/total-quantity")
    public Double getTotalQuantity(@PathVariable Long ingredientId) {
        return recipeIngredientService.getTotalQuantityOfIngredient(ingredientId);
    }
}
