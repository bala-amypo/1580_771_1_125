package com.example.demo.service.impl;

import com.example.demo.entity.Ingredient;
import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.IngredientRepository;
import com.example.demo.service.IngredientService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class IngredientServiceImpl implements IngredientService {

    private final IngredientRepository ingredientRepository;

    public IngredientServiceImpl(IngredientRepository ingredientRepository) {
        this.ingredientRepository = ingredientRepository;
    }

    @Override
    public Ingredient createIngredient(Ingredient ingredient) {
        ingredientRepository.findByNameIgnoreCase(ingredient.getName())
                .ifPresent(i -> { throw new BadRequestException("Ingredient name must be unique"); });
        if (ingredient.getCostPerUnit().compareTo(BigDecimal.ZERO) <= 0) {
            throw new BadRequestException("Cost per unit must be greater than 0");
        }
        ingredient.setActive(true);
        return ingredientRepository.save(ingredient);
    }

    @Override
    public Ingredient updateIngredient(Long id, Ingredient updated) {
        Ingredient existing = ingredientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ingredient not found"));
        existing.setName(updated.getName());
        existing.setUnit(updated.getUnit());
        if (updated.getCostPerUnit().compareTo(BigDecimal.ZERO) <= 0) {
            throw new BadRequestException("Cost per unit must be greater than 0");
        }
        existing.setCostPerUnit(updated.getCostPerUnit());
        return ingredientRepository.save(existing);
    }

    @Override
    public Ingredient getIngredientById(Long id) {
        return ingredientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ingredient not found"));
    }

    @Override
    public List<Ingredient> getAllIngredients() {
        return ingredientRepository.findAll();
    }

    @Override
    public void deactivateIngredient(Long id) {
        Ingredient ingredient = ingredientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ingredient not found"));
        ingredient.setActive(false);
        ingredientRepository.save(ingredient);
    }
}
