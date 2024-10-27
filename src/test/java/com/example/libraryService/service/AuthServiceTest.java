package com.example.libraryService.service;

import com.example.libraryService.dto.ReqRes;
import com.example.libraryService.entity.User;
import com.example.libraryService.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AuthServiceTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private AuthService authService;

    private User user;

    private ReqRes request;

    private ReqRes response;

    private Authentication authentication;

    @BeforeEach
    public void setUp() {
        user = new User(1L, "User", "usepass", "USER");

        request = new ReqRes();
        request.setUsername("User");
        request.setPassword("usepass");
        request.setRole("USER");
        request.setToken("rojzdsmbosmdz");

        authentication = new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword());
    }

    @Test
    @DisplayName("Sign up")
    @Order(1)
    public void signUp() {
        Mockito.when(userRepository.save(Mockito.any(User.class))).thenReturn(user);

        response = authService.signUp(request);
        System.out.println(response);
        Assertions.assertThat(response.getUser()).isEqualTo(user);
        Assertions.assertThat(response.getStatusCode()).isEqualTo(201);
        Assertions.assertThat(response.getMessage()).isEqualTo("User saved successfully");
    }

    @Test
    @DisplayName("Sign in")
    @Order(2)
    public void signIn() {
        Mockito.when(userRepository.findByUsername(Mockito.anyString())).thenReturn(Optional.of(user));
        Mockito.when(jwtUtil.generateToken(Mockito.any(User.class))).thenReturn("iurrhgeoanvoiier");
        Mockito.when(authenticationManager.authenticate(Mockito.any(Authentication.class))).thenReturn(authentication);

        response = authService.signIn(request);
        System.out.println(response);
        Assertions.assertThat(response.getToken()).isEqualTo("iurrhgeoanvoiier");
        Assertions.assertThat(response.getExpirationTime()).isEqualTo("5 min");
        Assertions.assertThat(response.getStatusCode()).isEqualTo(200);
        Assertions.assertThat(response.getMessage()).isEqualTo("Successfully signed in");
    }

    @Test
    @DisplayName("Refresh token")
    @Order(3)
    public void refreshToken() {
        Mockito.when(userRepository.findByUsername(Mockito.anyString())).thenReturn(Optional.of(user));
        Mockito.when(jwtUtil.extractUsername(Mockito.anyString())).thenReturn(user.getUsername());
        Mockito.when(jwtUtil.isTokenValid(Mockito.anyString(), Mockito.any(User.class))).thenReturn(true);
        Mockito.when(jwtUtil.generateToken(Mockito.any(User.class))).thenReturn("roiihrgaeori");

        response = authService.refreshToken(request);
        System.out.println(response);
        Assertions.assertThat(response.getToken()).isEqualTo("roiihrgaeori");
        Assertions.assertThat(response.getExpirationTime()).isEqualTo("5 min");
        Assertions.assertThat(response.getStatusCode()).isEqualTo(200);
        Assertions.assertThat(response.getMessage()).isEqualTo("Token was refreshed successfully");
    }
}
