package com.example.demo.service.impl;

import com.example.demo.entity.Category;
import com.example.demo.entity.MenuItem;
import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.MenuItemRepository;
import com.example.demo.repository.RecipeIngredientRepository;
import com.example.demo.service.MenuItemService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class MenuItemServiceImpl implements MenuItemService {

    private final MenuItemRepository menuItemRepository;
    private final RecipeIngredientRepository recipeIngredientRepository;
    private final CategoryRepository categoryRepository;

    public MenuItemServiceImpl(MenuItemRepository menuItemRepository,
                               RecipeIngredientRepository recipeIngredientRepository,
                               CategoryRepository categoryRepository) {
        this.menuItemRepository = menuItemRepository;
        this.recipeIngredientRepository = recipeIngredientRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public MenuItem createMenuItem(MenuItem menuItem) {
        menuItemRepository.findByNameIgnoreCase(menuItem.getName())
                .ifPresent(m -> { throw new BadRequestException("Menu item name must be unique"); });

        if (menuItem.getSellingPrice().compareTo(BigDecimal.ZERO) <= 0) {
            throw new BadRequestException("Selling price must be greater than 0");
        }

        // Validate categories
        if (menuItem.getCategories() != null) {
            Set<Category> activeCategories = new HashSet<>();
            for (Category c : menuItem.getCategories()) {
                Category existing = categoryRepository.findById(c.getId())
                        .orElseThrow(() -> new BadRequestException("Category not found"));
                if (!existing.isActive()) {
                    throw new BadRequestException("Cannot assign inactive category");
                }
                activeCategories.add(existing);
            }
            menuItem.setCategories(activeCategories);
        }

        return menuItemRepository.save(menuItem);
    }

    @Override
    public MenuItem updateMenuItem(Long id, MenuItem updated) {
        MenuItem existing = menuItemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Menu item not found"));

        if (updated.getSellingPrice().compareTo(BigDecimal.ZERO) <= 0) {
            throw new BadRequestException("Selling price must be greater than 0");
        }

        existing.setName(updated.getName());
        existing.setDescription(updated.getDescription());
        existing.setSellingPrice(updated.getSellingPrice());

        if (updated.getCategories() != null) {
            Set<Category> activeCategories = new HashSet<>();
            for (Category c : updated.getCategories()) {
                Category category = categoryRepository.findById(c.getId())
                        .orElseThrow(() -> new BadRequestException("Category not found"));
                if (!category.isActive()) {
                    throw new BadRequestException("Cannot assign inactive category");
                }
                activeCategories.add(category);
            }
            existing.setCategories(activeCategories);
        }

        // Validate activation: must have recipe ingredients
        if (updated.isActive() && !recipeIngredientRepository.existsByMenuItemId(id)) {
            throw new BadRequestException("Menu item cannot be active without recipe ingredients");
        }
        existing.setActive(updated.isActive());

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
        MenuItem menuItem = menuItemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Menu item not found"));
        menuItem.setActive(false);
        menuItemRepository.save(menuItem);
    }
}
