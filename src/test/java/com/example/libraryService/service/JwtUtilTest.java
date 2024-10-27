package com.example.libraryService.service;

import com.example.libraryService.entity.User;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class JwtUtilTest {
    @InjectMocks
    private JwtUtil jwtUtil;

    private User user;

    @BeforeEach
    public void setUp() {
        user = new User(1L, "Developer", "usepass", "USER");
    }

    @Test
    @DisplayName("Generate token")
    @Order(1)
    public void generateToken() {
        String token = jwtUtil.generateToken(user);

        System.out.println(token);
        assertTrue(token != null && !token.isEmpty());
    }

    @Test
    @DisplayName("Extract username")
    @Order(2)
    public void extractUsername() {
        String token = jwtUtil.generateToken(user);
        String username = jwtUtil.extractUsername(token);

        System.out.println(username);
        assertTrue(username != null && !username.isEmpty() && username.equals(user.getUsername()));
    }

    @Test
    @DisplayName("Is token valid")
    @Order(3)
    public void isTokenValid() {
        String token = jwtUtil.generateToken(user);
        boolean isValid = jwtUtil.isTokenValid(token, user);

        System.out.println(isValid);
        assertTrue(isValid);
    }

    @Test
    @DisplayName("Is token expired")
    @Order(4)
    public void isTokenExpired() {
        String token = jwtUtil.generateToken(user);
        boolean isExpired = jwtUtil.isTokenExpired(token);

        System.out.println(isExpired);
        assertFalse(isExpired);
    }
}
