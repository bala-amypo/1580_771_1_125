package com.example.demo.service.impl;

import com.example.demo.entity.Category;
import com.example.demo.entity.MenuItem;
import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.MenuItemRepository;
import com.example.demo.repository.RecipeIngredientRepository;
import com.example.demo.service.MenuItemService;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MenuItemServiceImpl implements MenuItemService {

    private final MenuItemRepository menuItemRepository;
    private final RecipeIngredientRepository recipeIngredientRepository;
    private final CategoryRepository categoryRepository;

    public MenuItemServiceImpl(
            MenuItemRepository menuItemRepository,
            RecipeIngredientRepository recipeIngredientRepository,
            CategoryRepository categoryRepository
    ) {
        this.menuItemRepository = menuItemRepository;
        this.recipeIngredientRepository = recipeIngredientRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public MenuItem createMenuItem(MenuItem menuItem) {
        menuItemRepository.findByNameIgnoreCase(menuItem.getName())
                .ifPresent(m -> {
                    throw new BadRequestException("Menu item name already exists");
                });

        if (menuItem.getSellingPrice() == null ||
            menuItem.getSellingPrice().compareTo(BigDecimal.ZERO) <= 0) {
            throw new BadRequestException("Invalid selling price");
        }

        if (menuItem.getCategories() != null && !menuItem.getCategories().isEmpty()) {
            Set<Category> validated = new HashSet<>();
            for (Category c : menuItem.getCategories()) {
                Category category = categoryRepository.findById(c.getId())
                        .orElseThrow(() -> new ResourceNotFoundException("Category not found"));
                if (!category.isActive()) {
                    throw new BadRequestException("Inactive category cannot be assigned");
                }
                validated.add(category);
            }
            menuItem.setCategories(validated);
        }

        return menuItemRepository.save(menuItem);
    }

    @Override
    public MenuItem updateMenuItem(Long id, MenuItem updated) {
        MenuItem existing = getMenuItemById(id);

        if (updated.isActive() &&
            !recipeIngredientRepository.existsByMenuItemId(id)) {
            throw new BadRequestException(
                    "Menu item cannot be active without recipe ingredients");
        }

        existing.setName(updated.getName());
        existing.setDescription(updated.getDescription());
        existing.setSellingPrice(updated.getSellingPrice());
        existing.setActive(updated.isActive());

        if (updated.getCategories() != null) {
            Set<Category> validated = new HashSet<>();
            for (Category c : updated.getCategories()) {
                Category category = categoryRepository.findById(c.getId())
                        .orElseThrow(() -> new ResourceNotFoundException("Category not found"));
                if (!category.isActive()) {
                    throw new BadRequestException("Inactive category cannot be assigned");
                }
                validated.add(category);
            }
            existing.setCategories(validated);
        }

        return menuItemRepository.save(existing);
    }

    @Override
    public MenuItem getMenuItemById(Long id) {
        return menuItemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Menu item not found"));
    }

    @Override
    public List<MenuItem> getAllMenuItems() {
        return menuItemRepository.findAll();
    }

    @Override
    public void deactivateMenuItem(Long id) {
        MenuItem item = getMenuItemById(id);
        item.setActive(false);
        menuItemRepository.save(item);
    }
}
