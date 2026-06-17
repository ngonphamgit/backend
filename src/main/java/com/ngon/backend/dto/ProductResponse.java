package com.ngon.backend.dto;

import jakarta.validation.constraints.NotBlank;

import java.math.BigDecimal;

public record ProductResponse(
        @NotBlank String name,
        @NotBlank int quantity,
        @NotBlank BigDecimal price,
        @NotBlank String category
)
{}
