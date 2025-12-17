package com.example.demo.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Ingredient;
import com.example.demo.repository.IngredientRepository;
import com.example.demo.service.IngredientService;

@Service
public class IngredientServiceImpl implements IngredientService{

    @Autowired 
    private IngredientRepository ingredientrepository;

    @Override
    public Ingredient createIngredient(Ingredient ingredient){
        return ingredientrepository.save(ingredient);
    }
    // @Override
    // public Ingredient updateIngredient(Long id,Ingredient ingredient){
    // }
    // @Override
    // public Optional<Ingredient> getIngredientById(Long id){
    //     return ingredientrepository.findById(id);
    // }
    // public List<Ingredient> getAllIngredients(){
    //     return ingredientrepository.findAll();
    // }
}