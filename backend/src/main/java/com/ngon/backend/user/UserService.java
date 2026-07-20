package com.ngon.backend.user;

import com.ngon.backend.exception.UserNotFoundException;
import com.ngon.backend.favorite.FavoriteResponse;
import com.ngon.backend.favorite.FavoriteService;
import com.ngon.backend.order.Order;
import com.ngon.backend.order.OrderResponse;
import com.ngon.backend.order.OrderService;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class UserService
{
    private final UserRepository userRepo;
    private final OrderService orderService;
    private final FavoriteService favoriteService;

    public UserService(UserRepository userRepo, OrderService orderService, FavoriteService favoriteService)
    {
        this.userRepo = userRepo;
        this.orderService = orderService;
        this.favoriteService = favoriteService;
    }

    public UserProfileResponse getMe(Authentication auth)
    {
        User user = userRepo.findByUsername(auth.getName());
    
        return new UserProfileResponse(
            toUserResponse(user),
            (orderService.getUserOrders(user).stream()).map(orderService::orderToResponse).toList(),
            (favoriteService.getUserFavorites(user).stream()).map(favoriteService::toFavoriteResponse).toList()
        );
    }

    public void deleteUser(DeleteUserRequest request)
    {
        User user = userRepo.findByUsername(request.username());

        if (user == null)
        {
            throw new UserNotFoundException("User not found");
        }

        userRepo.delete(user);
    }

    public UserResponse toUserResponse(User user)
    {
        return new UserResponse(
            user.getUsername(),
            user.getEmail()
        );
    }
}
