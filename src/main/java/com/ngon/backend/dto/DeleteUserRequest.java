package com.ngon.backend.dto;

import jakarta.validation.constraints.NotBlank;

public record DeleteUserRequest(@NotBlank String username) {}