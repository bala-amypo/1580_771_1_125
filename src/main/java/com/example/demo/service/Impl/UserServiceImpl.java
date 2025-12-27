// package com.example.demo.service.impl;

// import com.example.demo.dto.RegisterRequest;
// import com.example.demo.entity.User;
// import com.example.demo.exception.BadRequestException;
// import com.example.demo.repository.UserRepository;
// import com.example.demo.service.UserService;
// import org.springframework.security.crypto.password.PasswordEncoder;
// import org.springframework.stereotype.Service;
// @Service
// public class UserServiceImpl implements UserService {

//     private final UserRepository userRepository;
//     private final PasswordEncoder passwordEncoder;

//     public UserServiceImpl(UserRepository userRepository,
//                            PasswordEncoder passwordEncoder) {
//         this.userRepository = userRepository;
//         this.passwordEncoder = passwordEncoder;
//     }

//     @Override
//     public User register(RegisterRequest req) {

//         userRepository.findByEmailIgnoreCase(req.getEmail())
//                 .ifPresent(u -> {
//                     throw new BadRequestException("Email already in use");
//                 });

//         User user = new User();
//         user.setEmail(req.getEmail());
//         user.setFullName(req.getFullName());
//         user.setPassword(passwordEncoder.encode(req.getPassword()));
//         user.setRole(req.getRole());

//         return userRepository.save(user);
//     }
//     @Override
//     public User findByEmail(String email) {
//         return userRepository.findByEmailIgnoreCase(email)
//             .orElseThrow(() -> new RuntimeException("User not found"));
//     }   
// }

package com.example.demo.service.impl;

import com.example.demo.dto.RegisterRequest;
import com.example.demo.entity.User;
import com.example.demo.exception.BadRequestException;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User register(RegisterRequest request) {
        if (userRepository.findByEmailIgnoreCase(request.getEmail()).isPresent()) {
            throw new BadRequestException("Email already in use");
        }
        
        User user = new User();
        user.setFullName(request.getFullName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(request.getRole() != null && !request.getRole().isEmpty() ? request.getRole() : "MANAGER");
        user.setActive(true);
        
        return userRepository.save(user);
    }

    @Override
    public User findByEmailIgnoreCase(String email) {
        return userRepository.findByEmailIgnoreCase(email).orElse(null);
    }
}