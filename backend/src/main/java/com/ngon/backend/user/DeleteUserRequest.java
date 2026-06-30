package com.ngon.backend.user;

import jakarta.validation.constraints.NotBlank;

public record DeleteUserRequest(@NotBlank String username) {}