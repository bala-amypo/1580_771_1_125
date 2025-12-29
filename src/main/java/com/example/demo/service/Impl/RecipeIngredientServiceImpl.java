package com.example.demo.service.impl;

import com.example.demo.entity.Ingredient;
import com.example.demo.entity.MenuItem;
import com.example.demo.entity.RecipeIngredient;
import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.ResourceNotFoundException;
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

    public RecipeIngredientServiceImpl(RecipeIngredientRepository recipeIngredientRepository,
                                       IngredientRepository ingredientRepository,
                                       MenuItemRepository menuItemRepository) {
        this.recipeIngredientRepository = recipeIngredientRepository;
        this.ingredientRepository = ingredientRepository;
        this.menuItemRepository = menuItemRepository;
    }

    @Override
    public RecipeIngredient addIngredientToMenuItem(Long menuItemId, Long ingredientId, Double quantity) {
        if (quantity == null || quantity <= 0) {
            throw new BadRequestException("Quantity must be greater than 0");
        }
        
        MenuItem menuItem = menuItemRepository.findById(menuItemId)
            .orElseThrow(() -> new ResourceNotFoundException("Menu item not found"));
        
        Ingredient ingredient = ingredientRepository.findById(ingredientId)
            .orElseThrow(() -> new ResourceNotFoundException("Ingredient not found"));
        
        RecipeIngredient recipeIngredient = new RecipeIngredient();
        recipeIngredient.setMenuItem(menuItem);
        recipeIngredient.setIngredient(ingredient);
        recipeIngredient.setQuantityRequired(quantity);
        
        return recipeIngredientRepository.save(recipeIngredient);
    }

    @Override
    public RecipeIngredient addIngredientToMenuItem(RecipeIngredient recipeIngredient) {
        if (recipeIngredient.getQuantityRequired() == null || recipeIngredient.getQuantityRequired() <= 0) {
            throw new BadRequestException("Quantity must be greater than 0");
        }
        
        if (recipeIngredient.getIngredient() == null || recipeIngredient.getIngredient().getId() == null) {
            throw new ResourceNotFoundException("Ingredient not found");
        }
        
        if (recipeIngredient.getMenuItem() == null || recipeIngredient.getMenuItem().getId() == null) {
            throw new ResourceNotFoundException("Menu item not found");
        }
        
        Ingredient ingredient = ingredientRepository.findById(recipeIngredient.getIngredient().getId())
            .orElseThrow(() -> new ResourceNotFoundException("Ingredient not found"));
        
        MenuItem menuItem = menuItemRepository.findById(recipeIngredient.getMenuItem().getId())
            .orElseThrow(() -> new ResourceNotFoundException("Menu item not found"));
        
        recipeIngredient.setIngredient(ingredient);
        recipeIngredient.setMenuItem(menuItem);
        
        return recipeIngredientRepository.save(recipeIngredient);
    }

    @Override
    public RecipeIngredient updateRecipeIngredient(Long id, Double quantity) {
        if (quantity == null || quantity <= 0) {
            throw new BadRequestException("Quantity must be greater than 0");
        }
        
        RecipeIngredient recipeIngredient = recipeIngredientRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Recipe ingredient not found"));
        
        recipeIngredient.setQuantityRequired(quantity);
        return recipeIngredientRepository.save(recipeIngredient);
    }

    @Override
    public List<RecipeIngredient> getIngredientsByMenuItem(Long menuItemId) {
        return recipeIngredientRepository.findByMenuItemId(menuItemId);
    }

    @Override
    public void removeIngredientFromRecipe(Long id) {
        if (!recipeIngredientRepository.existsById(id)) {
            throw new ResourceNotFoundException("Recipe ingredient not found");
        }
        recipeIngredientRepository.deleteById(id);
    }

    @Override
    public Double getTotalQuantityOfIngredient(Long ingredientId) {
        return recipeIngredientRepository.getTotalQuantityByIngredientId(ingredientId);
    }
}