package com.example.demo.controller;

import com.example.demo.entity.RecipeIngredient;
import com.example.demo.service.RecipeIngredientService;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<RecipeIngredient> addIngredientToMenuItem(@RequestBody RecipeIngredient ri) {
        return new ResponseEntity<>(recipeIngredientService.addIngredientToMenuItem(ri), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RecipeIngredient> updateRecipeIngredient(@PathVariable Long id,
                                                                   @RequestBody Double quantity) {
        return ResponseEntity.ok(recipeIngredientService.updateRecipeIngredient(id, quantity));
    }

    @GetMapping("/menu-item/{menuItemId}")
    public ResponseEntity<List<RecipeIngredient>> getIngredientsByMenuItem(@PathVariable Long menuItemId) {
        return ResponseEntity.ok(recipeIngredientService.getIngredientsByMenuItem(menuItemId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeIngredientFromRecipe(@PathVariable Long id) {
        recipeIngredientService.removeIngredientFromRecipe(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/ingredient/{ingredientId}/total-quantity")
    public ResponseEntity<Double> getTotalQuantityOfIngredient(@PathVariable Long ingredientId) {
        return ResponseEntity.ok(recipeIngredientService.getTotalQuantityOfIngredient(ingredientId));
    }
}
