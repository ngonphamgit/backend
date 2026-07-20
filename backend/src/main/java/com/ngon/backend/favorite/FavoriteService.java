package com.ngon.backend.favorite;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.ngon.backend.product.Product;
import com.ngon.backend.product.ProductService;
import com.ngon.backend.user.User;
import com.ngon.backend.user.UserRepository;
import com.ngon.backend.user.UserService;

@Service
public class FavoriteService {
    private final FavoriteRepository favoriteRepo;
    private final UserRepository userRepo;
    private final UserService userService;
    private final ProductService productService;

    public FavoriteService(FavoriteRepository favoriteRepo, UserRepository userRepo, UserService userService, ProductService productService)
    {
        this.favoriteRepo = favoriteRepo;
        this.userRepo = userRepo;
        this.userService = userService;
        this.productService = productService;
    }

    public List<Favorite> getUserFavorites(User user)
    {
        return favoriteRepo.findAllByUserId(user.getId());
    }

    public FavoriteResponse toFavoriteResponse(Favorite favorite)
    {
        return new FavoriteResponse(
            favorite.getId(),
            userService.toUserResponse(favorite.getUser()),
            productService.toResponse(favorite.getProduct())
        );
    }
}
