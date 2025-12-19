package com.example.demo.controller;

import com.example.demo.model.MenuItem;
import com.example.demo.service.MenuItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/menu-items")
public class MenuItemController {

    @Autowired
    private MenuItemService menuItemService;

    @PostMapping
    public MenuItem createMenuItem(@RequestBody MenuItem item) {
        return menuItemService.createMenuItem(item);
    }

    @PutMapping("/{id}")
    public MenuItem updateMenuItem(@PathVariable Long id, @RequestBody MenuItem item) {
        return menuItemService.updateMenuItem(id, item);
    }

    @GetMapping("/{id}")
    public MenuItem getMenuItem(@PathVariable Long id) {
        return menuItemService.getMenuItemById(id);
    }

    @GetMapping
    public List<MenuItem> getAllMenuItems() {
        return menuItemService.getAllMenuItems();
    }

    @PutMapping("/{id}/deactivate")
    public String deactivateMenuItem(@PathVariable Long id) {
        menuItemService.deactivateMenuItem(id);
        return "Menu item deactivated successfully";
    }
}
