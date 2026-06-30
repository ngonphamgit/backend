package com.ngon.backend.product;

import jakarta.validation.constraints.NotBlank;

import java.math.BigDecimal;

public record CreateProductRequest(
        @NotBlank String name,
        @NotBlank int quantity,
        @NotBlank BigDecimal price,
        @NotBlank String category
)
{}
