package com.ngon.backend.controller;

import com.ngon.backend.dto.AuthResponse;
import com.ngon.backend.dto.LoginRequest;
import com.ngon.backend.dto.RegisterRequest;
import com.ngon.backend.service.AuthService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController()
@RequestMapping("/auth")
public class AuthController
{
    private final AuthService authService;

    public AuthController(AuthService authService)
    {
        this.authService = authService;
    }

    @PostMapping("/register")
    public void registerUser(@RequestBody RegisterRequest request)
    {
        authService.registerUser(request);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> loginUser(@RequestBody LoginRequest request)
    {
        return ResponseEntity.ok(authService.loginUser(request));
    }
}
