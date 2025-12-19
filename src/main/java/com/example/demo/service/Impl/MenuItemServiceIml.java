package com.example.demo.service.impl;

import com.example.demo.model.Category;
import com.example.demo.model.MenuItem;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.MenuItemRepository;
import com.example.demo.service.MenuItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class MenuItemServiceImpl implements MenuItemService {

    @Autowired
    private MenuItemRepository menuItemRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public MenuItem createMenuItem(MenuItem item) {

        if (item.getSellingPrice() == null ||
                item.getSellingPrice().compareTo(BigDecimal.ZERO) <= 0) {
            throw new RuntimeException("Selling price must be greater than zero");
        }

        if (menuItemRepository.findByNameIgnoreCase(item.getName()).isPresent()) {
            throw new RuntimeException("Menu item name already exists");
        }

        Set<Category> validatedCategories = new HashSet<>();
        if (item.getCategories() != null) {
            for (Category c : item.getCategories()) {
                Category category = categoryRepository.findById(c.getId())
                        .orElseThrow(() -> new RuntimeException("Category not found"));

                if (!category.isActive()) {
                    throw new RuntimeException("Inactive category cannot be assigned");
                }
                validatedCategories.add(category);
            }
        }

        item.setCategories(validatedCategories);
        return menuItemRepository.save(item);
    }

    @Override
    public MenuItem updateMenuItem(Long id, MenuItem item) {

        MenuItem existing = menuItemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Menu item not found"));

        existing.setName(item.getName());
        existing.setDescription(item.getDescription());
        existing.setSellingPrice(item.getSellingPrice());

        if (item.getCategories() != null) {
            Set<Category> validatedCategories = new HashSet<>();
            for (Category c : item.getCategories()) {
                Category category = categoryRepository.findById(c.getId())
                        .orElseThrow(() -> new RuntimeException("Category not found"));
                if (!category.isActive()) {
                    throw new RuntimeException("Inactive category cannot be assigned");
                }
                validatedCategories.add(category);
            }
            existing.setCategories(validatedCategories);
        }

        return menuItemRepository.save(existing);
    }

    @Override
    public MenuItem getMenuItemById(Long id) {
        return menuItemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Menu item not found"));
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
