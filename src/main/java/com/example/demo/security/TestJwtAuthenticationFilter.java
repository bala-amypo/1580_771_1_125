package com.example.demo.security;

import com.example.demo.security.CustomUserDetailsService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import java.nio.charset.StandardCharsets;
import java.security.Key;

import static org.testng.Assert.*;

public class TestJwtAuthenticationFilter {

    private JwtAuthenticationFilter filter;
    private CustomUserDetailsService userDetailsService;

    private final String SECRET = "mysecretkeymysecretkey"; // must match your JwtUtil secret

    @BeforeMethod
    public void setUp() {
        userDetailsService = new CustomUserDetailsService();
        filter = new JwtAuthenticationFilter(userDetailsService);
        SecurityContextHolder.clearContext();
    }

    @AfterMethod
    public void tearDown() {
        SecurityContextHolder.clearContext();
    }

    @Test
    public void testDoFilterValidToken() throws Exception {
        Key key = Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));
        String token = Jwts.builder()
                .setSubject("testuser@example.com")
                .signWith(key)
                .compact();

        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("Authorization", "Bearer " + token);
        MockHttpServletResponse response = new MockHttpServletResponse();

        FilterChain chain = new FilterChain() {
            @Override
            public void doFilter(HttpServletRequest request, HttpServletResponse response) {
                // do nothing, just continue chain
            }
        };

        filter.doFilterInternal(request, response, chain);

        assertNotNull(SecurityContextHolder.getContext().getAuthentication());
        assertEquals(SecurityContextHolder.getContext().getAuthentication().getName(), "testuser@example.com");
    }

    @Test
    public void testDoFilterNoToken() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();

        FilterChain chain = new FilterChain() {
            @Override
            public void doFilter(HttpServletRequest request, HttpServletResponse response) {
                // continue chain
            }
        };

        filter.doFilterInternal(request, response, chain);

        assertNull(SecurityContextHolder.getContext().getAuthentication());
    }
}
