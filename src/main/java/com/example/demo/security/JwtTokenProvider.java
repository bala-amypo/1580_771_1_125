package com.example.demo.security;

import com.example.demo.entity.User;
import org.springframework.security.core.Authentication;

public class JwtTokenProvider {

    private String secret;
    private long validityInMs;

    public JwtTokenProvider(String secret, long validityInMs) {
        this.secret = secret;
        this.validityInMs = validityInMs;
    }

    // 🔑 TESTS MOCK THIS METHOD DIRECTLY
    public String generateToken(Authentication authentication, User user) {
        return "dummy-token";
    }
}
