package com.ngon.backend.favorite;

import com.ngon.backend.product.ProductResponse;
import com.ngon.backend.user.UserResponse;

import jakarta.validation.constraints.NotBlank;

public record FavoriteResponse(
    @NotBlank Long id,
    @NotBlank UserResponse user,
    @NotBlank ProductResponse product
) 
{}
