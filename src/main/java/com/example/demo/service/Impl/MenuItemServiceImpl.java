// package com.example.demo.service.impl;

// import com.example.demo.entity.Category;
// import com.example.demo.entity.MenuItem;
// import com.example.demo.exception.BadRequestException;
// import com.example.demo.exception.ResourceNotFoundException;
// import com.example.demo.repository.CategoryRepository;
// import com.example.demo.repository.MenuItemRepository;
// import com.example.demo.repository.RecipeIngredientRepository;
// import com.example.demo.service.MenuItemService;
// import org.springframework.stereotype.Service;
// import java.math.BigDecimal;
// import java.util.HashSet;
// import java.util.Set;
// @Service
// public class MenuItemServiceImpl implements MenuItemService {

//     private final MenuItemRepository menuItemRepository;
//     private final RecipeIngredientRepository recipeIngredientRepository;
//     private final CategoryRepository categoryRepository;

//     public MenuItemServiceImpl(MenuItemRepository menuItemRepository,
//                                RecipeIngredientRepository recipeIngredientRepository,
//                                CategoryRepository categoryRepository) {
//         this.menuItemRepository = menuItemRepository;
//         this.recipeIngredientRepository = recipeIngredientRepository;
//         this.categoryRepository = categoryRepository;
//     }

//     @Override
//     public MenuItem createMenuItem(MenuItem item) {
//         if (item.getSellingPrice().compareTo(BigDecimal.ZERO) <= 0) {
//             throw new BadRequestException("Invalid selling price");
//         }

//         menuItemRepository.findByNameIgnoreCase(item.getName())
//                 .ifPresent(m -> {
//                     throw new BadRequestException("Duplicate menu item");
//                 });

//         Set<Category> validated = new HashSet<>();
//         if (item.getCategories() != null) {
//             for (Category c : item.getCategories()) {
//                 Category cat = categoryRepository.findById(c.getId())
//                         .orElseThrow(() -> new ResourceNotFoundException("Category not found"));
//                 if (!Boolean.TRUE.equals(cat.getActive())) {
//                     throw new BadRequestException("Inactive category");
//                 }
//                 validated.add(cat);
//             }
//         }
//         item.setCategories(validated);

//         return menuItemRepository.save(item);
//     }

//     @Override
//     public MenuItem updateMenuItem(Long id, MenuItem updated) {
//         MenuItem existing = menuItemRepository.findById(id)
//                 .orElseThrow(() -> new ResourceNotFoundException("Menu item not found"));

//         if (Boolean.TRUE.equals(updated.getActive()) &&
//                 !recipeIngredientRepository.existsByMenuItemId(id)) {
//             throw new BadRequestException("Recipe missing");
//         }

//         if (updated.getCategories() != null) {
//             Set<Category> validated = new HashSet<>();
//             for (Category c : updated.getCategories()) {
//                 Category cat = categoryRepository.findById(c.getId())
//                         .orElseThrow(() -> new ResourceNotFoundException("Category not found"));
//                 if (!Boolean.TRUE.equals(cat.getActive())) {
//                     throw new BadRequestException("Inactive category");
//                 }
//                 validated.add(cat);
//             }
//             existing.setCategories(validated);
//         }

//         existing.setName(updated.getName());
//         existing.setDescription(updated.getDescription());
//         existing.setSellingPrice(updated.getSellingPrice());
//         existing.setActive(updated.getActive());

//         return menuItemRepository.save(existing);
//     }

//     @Override
//     public MenuItem getMenuItemById(Long id) {
//         return menuItemRepository.findById(id)
//                 .orElseThrow(() -> new ResourceNotFoundException("Menu item not found"));
//     }

//     @Override
//     public java.util.List<MenuItem> getAllMenuItems() {
//         return menuItemRepository.findAll();
//     }

//     @Override
//     public void deactivateMenuItem(Long id) {
//         MenuItem item = getMenuItemById(id);
//         item.setActive(false);
//         menuItemRepository.save(item);
//     }
// }
