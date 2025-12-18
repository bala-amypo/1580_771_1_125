package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Ingredient;
import com.example.demo.service.IngredientService;

@RestController
@RequestMapping("/api/ingredient")
public class IngredientController {
    @Autowired
    IngredientService ingredientService;
    @PostMapping
    public ResponseEntity<Ingredient> createIngredient(@RequestBody Ingredient ingredient) {
        Ingredient savedIngredient = ingredientService.createIngredient(ingredient);
        return ResponseEntity.status(201).body(savedIngredient);
    }
}
