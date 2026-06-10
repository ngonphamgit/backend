package com.ngon.backend.service;

import com.ngon.backend.dto.AuthResponse;
import com.ngon.backend.dto.LoginRequest;
import com.ngon.backend.entity.User;
import com.ngon.backend.dto.RegisterRequest;
import com.ngon.backend.repository.UserRepository;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class AuthService
{
    private final UserRepository userRepo;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthService(UserRepository userRepo, PasswordEncoder passwordEncoder, JwtService jwtService)
    {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public void registerUser(RegisterRequest request)
    {
        if (userRepo.existsByEmail(request.email()))
        {
            throw new RuntimeException("Email already exists");
        }

        User user = new User();
        user.setUsername(request.username());
        user.setEmail(request.email());
        user.setPassword(passwordEncoder.encode(request.password()));
        user.setRole("USER");

        userRepo.save(user);
    }

    public AuthResponse loginUser(LoginRequest request)
    {
        User user = userRepo.findByUsername(request.username());

        if (user == null)
        {
            throw new RuntimeException("Invalid login");
        }

        if (!passwordEncoder.matches(request.password(), user.getPassword()))
        {
            throw new RuntimeException("Invalid login");
        }

        String token = jwtService.generateToken(user.getEmail());
        return new AuthResponse(token);
    }
}
