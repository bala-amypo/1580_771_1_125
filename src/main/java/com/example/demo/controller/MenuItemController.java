package com.example.demo.controller;

import com.example.demo.entity.MenuItem;
import com.example.demo.service.MenuItemService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/menu-items")
public class MenuItemController {

    private final MenuItemService menuItemService;

    public MenuItemController(MenuItemService menuItemService) {
        this.menuItemService = menuItemService;
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<MenuItem> createMenuItem(@RequestBody MenuItem item) {
        return new ResponseEntity<>(
            menuItemService.createMenuItem(item),
            HttpStatus.CREATED
    );
}


    @PutMapping("/{id}")
    public MenuItem updateMenuItem(@PathVariable Long id,
                                   @RequestBody MenuItem item) {
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
    public void deactivateMenuItem(@PathVariable Long id) {
        menuItemService.deactivateMenuItem(id);
    }
}
