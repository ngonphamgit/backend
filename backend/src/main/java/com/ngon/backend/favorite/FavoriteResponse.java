package com.ngon.backend.favorite;

import com.ngon.backend.product.ProductResponse;

import jakarta.validation.constraints.NotBlank;

public record FavoriteResponse(
    @NotBlank Long id,
    @NotBlank ProductResponse product
) 
{}
