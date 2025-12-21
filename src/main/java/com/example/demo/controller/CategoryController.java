package com.example.demo.controller;

import com.example.demo.entity.Category;
import com.example.demo.service.CategoryService;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping
    public ResponseEntity<Category> create(@RequestBody Category category) {
        return ResponseEntity.status(201).body(categoryService.createCategory(category));
    }

    @PutMapping("/{id}")
    public Category update(@PathVariable Long id, @RequestBody Category category) {
        return categoryService.updateCategory(id, category);
    }

    @GetMapping("/{id}")
    public Category getById(@PathVariable Long id) {
        return categoryService.getCategoryById(id);
    }

    @GetMapping
    public ResponseEntity<List<Category>> getAll() {
        return ResponseEntity.status(200).body(categoryService.getAllCategories());
    }

    @PutMapping("/{id}/deactivate")
    public String deactivate(@PathVariable Long id) {
        categoryService.deactivateCategory(id);
        return "Category deactivated successfully";
    }
}
