package com.example.libraryService.service;

import com.example.libraryService.dto.ReqRes;
import com.example.libraryService.entity.User;
import com.example.libraryService.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    public ReqRes signUp(ReqRes request) {
        ReqRes response = new ReqRes();

        try {
            User newUser = new User();
            newUser.setUsername(request.getUsername());
            newUser.setPassword(passwordEncoder.encode(request.getPassword()));
            newUser.setRole(request.getRole());
            User user = userRepository.save(newUser);

            if (user != null && user.getId() > 0) {
                response.setUser(user);
                response.setMessage("User saved successfully");
                response.setStatusCode(201);
            }
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setError(e.getMessage());
        }

        return response;
    }

    public ReqRes signIn(ReqRes request) {
        ReqRes response = new ReqRes();

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
            User user = userRepository.findByUsername(request.getUsername()).orElseThrow();
            String token = jwtUtil.generateToken(user);
            response.setRole(user.getRole());
            response.setStatusCode(200);
            response.setToken(token);
            response.setExpirationTime("5 min");
            response.setMessage("Successfully signed in");
        } catch (Exception e) {
            response.setStatusCode(404);
            response.setError(e.getMessage());
        }

        return response;
    }

    public ReqRes refreshToken(ReqRes request) {
        ReqRes response = new ReqRes();
        String username = jwtUtil.extractUsername(request.getToken());
        User user = userRepository.findByUsername(username).orElseThrow();

        if (jwtUtil.isTokenValid(request.getToken(), user)) {
            String newToken = jwtUtil.generateToken(user);
            response.setStatusCode(200);
            response.setToken(newToken);
            response.setExpirationTime("5 min");
            response.setMessage("Token was refreshed successfully");
        } else
            response.setStatusCode(404);

        return response;
    }
}
