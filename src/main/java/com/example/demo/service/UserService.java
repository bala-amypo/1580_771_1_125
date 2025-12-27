// package com.example.demo.service;

// import com.example.demo.dto.RegisterRequest;
// import com.example.demo.entity.User;

// public interface UserService {

//     User register(RegisterRequest req);

//     User findByEmail(String email);
// }



package com.example.demo.service;

import com.example.demo.dto.RegisterRequest;
import com.example.demo.entity.User;

public interface UserService {
    User register(RegisterRequest request);
    User findByEmailIgnoreCase(String email);
}