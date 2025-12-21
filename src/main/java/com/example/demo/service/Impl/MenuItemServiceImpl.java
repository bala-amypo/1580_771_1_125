package com.example.demo.service.impl;

import com.example.demo.entity.Category;
import com.example.demo.entity.MenuItem;
import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.MenuItemRepository;
import com.example.demo.service.MenuItemService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class MenuItemServiceImpl implements MenuItemService {

    private final MenuItemRepository menuItemRepository;
    private final CategoryRepository categoryRepository;

    public MenuItemServiceImpl(MenuItemRepository menuItemRepository,
                               CategoryRepository categoryRepository) {
        this.menuItemRepository = menuItemRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public MenuItem createMenuItem(MenuItem item) {

        if (item.getSellingPrice() == null ||
                item.getSellingPrice().compareTo(BigDecimal.ZERO) <= 0) {
            throw new BadRequestException("Selling price must be greater than zero");
        }

        if (menuItemRepository.findByNameIgnoreCase(item.getName()).isPresent()) {
            throw new BadRequestException("Menu item name already exists");
        }

        Set<Category> validatedCategories = new HashSet<>();
        if (item.getCategories() != null) {
            for (Category c : item.getCategories()) {
                Category category = categoryRepository.findById(c.getId())
                        .orElseThrow(() -> new ResourceNotFoundException("Category not found"));

                if (!category.isActive()) {
                    throw new BadRequestException("Inactive category cannot be assigned");
                }
                validatedCategories.add(category);
            }
        }

        item.setCategories(validatedCategories);
        item.setActive(true);
        return menuItemRepository.save(item);
    }

    @Override
    public MenuItem updateMenuItem(Long id, MenuItem item) {

        MenuItem existing = menuItemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Menu item not found"));

        if (item.getSellingPrice() != null &&
                item.getSellingPrice().compareTo(BigDecimal.ZERO) <= 0) {
            throw new BadRequestException("Selling price must be greater than zero");
        }

        existing.setName(item.getName());
        existing.setDescription(item.getDescription());
        existing.setSellingPrice(item.getSellingPrice());

        if (item.getCategories() != null) {
            Set<Category> validatedCategories = new HashSet<>();
            for (Category c : item.getCategories()) {
                Category category = categoryRepository.findById(c.getId())
                        .orElseThrow(() -> new ResourceNotFoundException("Category not found"));

                if (!category.isActive()) {
                    throw new BadRequestException("Inactive category cannot be assigned");
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
