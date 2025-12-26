package com.example.demo.controller;

import com.example.demo.entity.RecipeIngredient;
import com.example.demo.service.RecipeIngredientService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/recipe-ingredients")
public class RecipeIngredientController {

    private final RecipeIngredientService recipeIngredientService;

    public RecipeIngredientController(RecipeIngredientService recipeIngredientService) {
        this.recipeIngredientService = recipeIngredientService;
    }

    @PostMapping
    public ResponseEntity<RecipeIngredient> addIngredient(@RequestBody RecipeIngredient ri) {
        return ResponseEntity.ok(recipeIngredientService.addIngredientToMenuItem(ri));
    }

    @PutMapping("/{id}")
    public ResponseEntity<RecipeIngredient> updateIngredient(
            @PathVariable Long id,
            @RequestParam Double quantity) {
        return ResponseEntity.ok(recipeIngredientService.updateRecipeIngredient(id, quantity));
    }

    @GetMapping("/menu-item/{menuItemId}")
    public ResponseEntity<List<RecipeIngredient>> getByMenuItem(
            @PathVariable Long menuItemId) {
        return ResponseEntity.ok(recipeIngredientService.getIngredientsByMenuItem(menuItemId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteIngredient(@PathVariable Long id) {
        recipeIngredientService.removeIngredientFromRecipe(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/ingredient/{ingredientId}/total-quantity")
    public ResponseEntity<Double> getTotalQuantity(@PathVariable Long ingredientId) {
        return ResponseEntity.ok(
                recipeIngredientService.getTotalQuantityOfIngredient(ingredientId)
        );
    }
}
