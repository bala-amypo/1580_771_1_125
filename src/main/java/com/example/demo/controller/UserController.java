package com.example.demo.controller;

import com.example.demo.entity.User;
import com.example.demo.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // CREATE
    @PostMapping
    public User create(@RequestBody User user) {
        return userService.createUser(user);
    }

    // UPDATE
    @PutMapping("/{id}")
    public User update(@PathVariable Long id, @RequestBody User user) {
        return userService.updateUser(id, user);
    }

    // GET BY ID
    @GetMapping("/{id}")
    public User getById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    // GET ALL
    @GetMapping
    public List<User> getAll() {
        return userService.getAllUsers();
    }

    // DEACTIVATE
    @PutMapping("/{id}/deactivate")
    public String deactivate(@PathVariable Long id) {
        userService.deactivateUser(id);
        return "User deactivated successfully";
    }
}
