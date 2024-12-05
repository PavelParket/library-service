package com.example.libraryService.controller;

import com.example.libraryService.dto.ReqRes;
import com.example.libraryService.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "api/auth")
@Tag(name = "Authentication controller", description = "Sign up, sign in, refresh token")
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping("/signup")
    @Operation(summary = "SignUp", description = "Register a new user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User registered successfully"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<ReqRes> signUp(@RequestBody ReqRes request) {
        ReqRes response = authService.signUp(request);
        return response.getStatusCode() == 201 ?
                new ResponseEntity<>(response, HttpStatus.CREATED) :
                new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PostMapping("/signin")
    @Operation(summary = "SignIn", description = "Authenticate a user and return a token")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User authenticated successfully"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    public ResponseEntity<ReqRes> signIn(@RequestBody ReqRes request) {
        ReqRes response = authService.signIn(request);
        return response.getStatusCode() == 200 ?
                new ResponseEntity<>(response, HttpStatus.OK) :
                new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @PostMapping("/refresh")
    @Operation(summary = "Refresh", description = "Refresh the authentication token")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Token refreshed successfully"),
            @ApiResponse(responseCode = "404", description = "Token not found or invalid")
    })
    public ResponseEntity<ReqRes> refresh(@RequestBody ReqRes request) {
        ReqRes response = authService.refreshToken(request);
        return response.getStatusCode() == 200 ?
                new ResponseEntity<>(response, HttpStatus.OK) :
                new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
}
