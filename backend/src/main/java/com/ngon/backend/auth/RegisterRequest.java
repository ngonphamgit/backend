package com.ngon.backend.auth;

import jakarta.validation.constraints.NotBlank;

public record RegisterRequest
        (@NotBlank String username,
         @NotBlank String email,
         @NotBlank String password,
         @NotBlank String firstName,
         @NotBlank String lastName)
{}
