package com.ngon.backend.user;

import java.util.List;

import com.ngon.backend.favorite.FavoriteResponse;
import com.ngon.backend.order.OrderResponse;

public record UserProfileResponse (
    UserResponse user,
    List<OrderResponse> userOrders,
    List<FavoriteResponse> userFavorites
)
{}
