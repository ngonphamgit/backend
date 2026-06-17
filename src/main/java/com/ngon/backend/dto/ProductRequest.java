package com.ngon.backend.dto;

import jakarta.validation.constraints.NotBlank;

import java.math.BigDecimal;

public record ProductRequest(
        @NotBlank String name,
        @NotBlank int quantity,
        @NotBlank BigDecimal price,
        @NotBlank String category
)
{}
