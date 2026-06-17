package com.ngon.backend.dto;

import jakarta.validation.constraints.NotBlank;

public record ProductResponse(
        @NotBlank String name,
        @NotBlank int quantity,
        @NotBlank float price,
        @NotBlank String category
)
{}
