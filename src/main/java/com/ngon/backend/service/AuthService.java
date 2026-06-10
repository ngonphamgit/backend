package com.ngon.backend.service;

import com.ngon.backend.entity.User;
import com.ngon.backend.dto.RegisterRequest;
import com.ngon.backend.repository.UserRepository;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class AuthService
{
    private final UserRepository userRepo;

    public AuthService(UserRepository userRepo)
    {
        this.userRepo = userRepo;
    }

    public void createUser(@RequestBody RegisterRequest request)
    {
        if (userRepo.existsByEmail(request.email()))
        {
            throw new RuntimeException("Email already exists");
        }

        User user = new User();
        user.setUsername(request.username());
        user.setEmail(request.email());
        user.setPassword(request.password());
        user.setRole("USER");

        userRepo.save(user);
    }
}
