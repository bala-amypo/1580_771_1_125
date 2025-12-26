package com.example.demo.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.*;
import org.mockito.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TestJwtAuthenticationFilter {

    private static final String SECRET = "secret-key";

    @Mock
    private CustomUserDetailsService userDetailsService;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private FilterChain filterChain;

    private JwtAuthenticationFilter filter;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        filter = new JwtAuthenticationFilter(userDetailsService, SECRET);
        SecurityContextHolder.clearContext();
    }

    @AfterEach
    void cleanup() {
        SecurityContextHolder.clearContext();
    }

    @Test
    void shouldAuthenticateUserWhenValidTokenProvided() throws Exception {
        // Arrange
        String email = "test@example.com";

        String token = Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 100000))
                .signWith(SignatureAlgorithm.HS256, SECRET)
                .compact();

        when(request.getHeader("Authorization"))
                .thenReturn("Bearer " + token);

        UserDetails userDetails = new User(
                email,
                "password",
                List.of(() -> "ROLE_MANAGER")
        );

        when(userDetailsService.loadUserByUsername(email))
                .thenReturn(userDetails);

        // Act
        filter.doFilterInternal(request, response, filterChain);

        // Assert
        Authentication auth =
                SecurityContextHolder.getContext().getAuthentication();

        assertNotNull(auth);
        assertEquals(email, auth.getName());
        assertTrue(auth.isAuthenticated());

        verify(userDetailsService).loadUserByUsername(email);
        verify(filterChain).doFilter(request, response);
    }

    @Test
    void shouldNotAuthenticateWhenTokenIsMissing() throws Exception {
        // Arrange
        when(request.getHeader("Authorization")).thenReturn(null);

        // Act
        filter.doFilterInternal(request, response, filterChain);

        // Assert
        assertNull(SecurityContextHolder.getContext().getAuthentication());
        verify(filterChain).doFilter(request, response);
        verifyNoInteractions(userDetailsService);
    }

    @Test
    void shouldNotAuthenticateWhenTokenIsInvalid() throws Exception {
        // Arrange
        when(request.getHeader("Authorization"))
                .thenReturn("Bearer invalid.jwt.token");

        // Act
        filter.doFilterInternal(request, response, filterChain);

        // Assert
        assertNull(SecurityContextHolder.getContext().getAuthentication());
        verify(filterChain).doFilter(request, response);
    }

    @Test
    void shouldIgnoreHeaderWithoutBearerPrefix() throws Exception {
        // Arrange
        when(request.getHeader("Authorization"))
                .thenReturn("Basic abc123");

        // Act
        filter.doFilterInternal(request, response, filterChain);

        // Assert
        assertNull(SecurityContextHolder.getContext().getAuthentication());
        verify(filterChain).doFilter(request, response);
    }
}
