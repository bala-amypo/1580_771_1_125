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
        if (menuItemRepository.findByNameIgnoreCase(menuItem.getName()).isPresent()) {
            throw new BadRequestException("Menu item name already exists");
        }
        
        if (menuItem.getSellingPrice() == null || menuItem.getSellingPrice().compareTo(BigDecimal.ZERO) <= 0) {
            throw new BadRequestException("Selling price must be greater than 0");
        }
        
        if (menuItem.getCategories() != null && !menuItem.getCategories().isEmpty()) {
            Set<Category> validCategories = new HashSet<>();
            for (Category category : menuItem.getCategories()) {
                Category existingCategory = categoryRepository.findById(category.getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Category not found"));
                
                if (!existingCategory.getActive()) {
                    throw new BadRequestException("Cannot assign inactive category");
                }
                validCategories.add(existingCategory);
            }
            menuItem.setCategories(validCategories);
        }
        
        return menuItemRepository.save(menuItem);
    }

    @Override
    public MenuItem updateMenuItem(Long id, MenuItem menuItem) {
        MenuItem existing = menuItemRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Menu item not found"));
        
        if (menuItem.getSellingPrice() != null && menuItem.getSellingPrice().compareTo(BigDecimal.ZERO) <= 0) {
            throw new BadRequestException("Selling price must be greater than 0");
        }
        
        if (menuItem.getActive() != null && menuItem.getActive() && !recipeIngredientRepository.existsByMenuItemId(id)) {
            throw new BadRequestException("Cannot activate menu item without recipe ingredients");
        }
        
        if (menuItem.getName() != null && !menuItem.getName().equalsIgnoreCase(existing.getName())) {
            if (menuItemRepository.findByNameIgnoreCase(menuItem.getName()).isPresent()) {
                throw new BadRequestException("Menu item name already exists");
            }
            existing.setName(menuItem.getName());
        }
        
        if (menuItem.getDescription() != null) {
            existing.setDescription(menuItem.getDescription());
        }
        
        if (menuItem.getSellingPrice() != null) {
            existing.setSellingPrice(menuItem.getSellingPrice());
        }
        
        if (menuItem.getActive() != null) {
            existing.setActive(menuItem.getActive());
        }
        
        if (menuItem.getCategories() != null) {
            Set<Category> validCategories = new HashSet<>();
            for (Category category : menuItem.getCategories()) {
                Category existingCategory = categoryRepository.findById(category.getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Category not found"));
                
                if (!existingCategory.getActive()) {
                    throw new BadRequestException("Cannot assign inactive category");
                }
                validCategories.add(existingCategory);
            }
            existing.setCategories(validCategories);
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
        MenuItem menuItem = getMenuItemById(id);
        menuItem.setActive(false);
        menuItemRepository.save(menuItem);
    }
}