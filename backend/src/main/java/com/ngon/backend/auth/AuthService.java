package com.ngon.backend.auth;

import com.ngon.backend.exception.EmailExistsException;
import com.ngon.backend.exception.InvalidLoginException;
import com.ngon.backend.exception.UsernameExistsException;
import com.ngon.backend.security.JwtService;
import com.ngon.backend.user.Role;
import com.ngon.backend.user.User;
import com.ngon.backend.user.UserRepository;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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
        if (userRepo.existsByUsername(request.username()))
        {
            throw new UsernameExistsException("Username already exists");
        }

        if (userRepo.existsByEmail(request.email()))
        {
            throw new EmailExistsException("Email already exists");
        }

        User user = new User();
        user.setUsername(request.username());
        user.setEmail(request.email());
        user.setPassword(passwordEncoder.encode(request.password()));
        user.setRole(Role.ROLE_USER);

        userRepo.save(user);
    }

    public AuthResponse loginUser(LoginRequest request)
    {
        User user = userRepo.findByUsername(request.username());

        if (user == null)
        {
            throw new InvalidLoginException("Invalid login");
        }

        if (!passwordEncoder.matches(request.password(), user.getPassword()))
        {
            throw new InvalidLoginException("Invalid login");
        }

        String token = jwtService.generateToken(user.getUsername());
        return new AuthResponse(token);
    }
}
