package com.example.demo.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.entity.Ingredient;
import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.IngredientRepository;
import com.example.demo.service.IngredientService;

@Service
public class IngredientServiceImpl implements IngredientService {

    private final IngredientRepository repo;

    public IngredientServiceImpl(IngredientRepository repo) {
        this.repo = repo;
    }

    @Override
    public Ingredient createIngredient(Ingredient ing) {
        if (repo.findByNameIgnoreCase(ing.getName()).isPresent()) {
            throw new BadRequestException("Ingredient already exists");
        }
        if (ing.getCostPerUnit().signum() <= 0) {
            throw new BadRequestException("Cost per unit must be positive");
        }
        ing.setActive(true);
        return repo.save(ing);
    }

    @Override
    public Ingredient updateIngredient(Long id, Ingredient updated) {
        Ingredient ing = repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ingredient not found"));
        ing.setName(updated.getName());
        ing.setUnit(updated.getUnit());
        ing.setCostPerUnit(updated.getCostPerUnit());
        return repo.save(ing);
    }

    @Override
    public Ingredient getIngredientById(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ingredient not found"));
    }

    @Override
    public List<Ingredient> getAllIngredients() {
        return repo.findAll();
    }

    @Override
    public void deactivateIngredient(Long id) {
        Ingredient ing = getIngredientById(id);
        ing.setActive(false);
        repo.save(ing);
    }
}
