package com.example.demo.controller;

import com.example.demo.entity.MenuItem;
import com.example.demo.service.MenuItemService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/menu-items")
public class MenuItemController {

    private final MenuItemService menuItemService;

    public MenuItemController(MenuItemService menuItemService) {
        this.menuItemService = menuItemService;
    }

    @PostMapping
    public MenuItem create(@RequestBody MenuItem item) {
        return menuItemService.createMenuItem(item);
    }

    @PutMapping("/{id}")
    public MenuItem update(@PathVariable Long id, @RequestBody MenuItem item) {
        return menuItemService.updateMenuItem(id, item);
    }

    @GetMapping("/{id}")
    public MenuItem getById(@PathVariable Long id) {
        return menuItemService.getMenuItemById(id);
    }

    @GetMapping
    public List<MenuItem> getAll() {
        return menuItemService.getAllMenuItems();
    }

    @PutMapping("/{id}/deactivate")
    public String deactivate(@PathVariable Long id) {
        menuItemService.deactivateMenuItem(id);
        return "Menu item deactivated successfully";
    }
}
