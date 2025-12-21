package com.example.demo.controller;

import com.example.demo.entity.RecipeIngredient;
import com.example.demo.service.RecipeIngredientService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/recipe-ingredients")
public class RecipeIngredientController {

    private final RecipeIngredientService recipeIngredientService;

    public RecipeIngredientController(RecipeIngredientService recipeIngredientService) {
        this.recipeIngredientService = recipeIngredientService;
    }

    // ADD INGREDIENT TO MENU ITEM
    @PostMapping
    public RecipeIngredient add(@RequestBody RecipeIngredient recipeIngredient) {
        return recipeIngredientService.addIngredientToMenuItem(recipeIngredient);
    }

    // UPDATE QUANTITY
    @PutMapping("/{id}")
    public RecipeIngredient updateQuantity(
            @PathVariable Long id,
            @RequestParam Double quantity) {
        return recipeIngredientService.updateRecipeIngredient(id, quantity);
    }

    // GET ALL INGREDIENTS FOR MENU ITEM
    @GetMapping("/menu-item/{menuItemId}")
    public List<RecipeIngredient> getByMenuItem(@PathVariable Long menuItemId) {
        return recipeIngredientService.getIngredientsByMenuItem(menuItemId);
    }

    // DELETE INGREDIENT FROM RECIPE
    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        recipeIngredientService.removeIngredientFromRecipe(id);
        return "Recipe ingredient removed successfully";
    }

    // GET TOTAL QUANTITY OF INGREDIENT
    @GetMapping("/ingredient/{ingredientId}/total-quantity")
    public Double getTotalQuantity(@PathVariable Long ingredientId) {
        return recipeIngredientService.getTotalQuantityOfIngredient(ingredientId);
    }
}
