package com.example.libraryService.controller;

import com.example.libraryService.dto.ReqRes;
import com.example.libraryService.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<ReqRes> signUp(@RequestBody ReqRes request) {
        return new ResponseEntity<>(authService.signUp(request), HttpStatus.CREATED);
    }

    @PostMapping("/signin")
    public ResponseEntity<ReqRes> signIn(@RequestBody ReqRes request) {
        ReqRes response = authService.signIn(request);
        return response.getStatusCode() == 202 ?
                new ResponseEntity<>(response, HttpStatus.ACCEPTED) :
                new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @PostMapping("/refresh")
    public ResponseEntity<ReqRes> refresh(@RequestBody ReqRes request) {
        return new ResponseEntity<>(authService.refreshToken(request), HttpStatus.OK);
    }
}
