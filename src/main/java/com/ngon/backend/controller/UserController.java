package com.ngon.backend.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController
{
    @GetMapping("/me")
    public String getUser(Authentication auth)
    {
        return auth.getName();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/delete")
    public String deleteUser()
    {
        return "admin access";
    }
}
