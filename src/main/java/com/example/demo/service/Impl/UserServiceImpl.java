package com.example.demo.service.impl;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.ResourceNotFoundException;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User createUser(User user) {

        if (userRepository.findByEmailIgnoreCase(user.getEmail()).isPresent()) {
            throw new BadRequestException("Email already exists");
        }

        if (userRepository.findByUsernameIgnoreCase(user.getUsername()).isPresent()) {
            throw new BadRequestException("Username already exists");
        }

        return userRepository.save(user);
    }

    @Override
    public User updateUser(Long id, User user) {

        User existing = getUserById(id);
        existing.setUsername(user.getUsername());
        existing.setEmail(user.getEmail());
        existing.setPassword(user.getPassword());
        existing.setRole(user.getRole());
        existing.setActive(user.isActive());
        return userRepository.save(existing);
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public void deactivateUser(Long id) {
        User user = getUserById(id);
        user.setActive(false);
        userRepository.save(user);
    }
}
