package com.ngon.backend.favorite;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ngon.backend.exception.FavoriteExistsException;
import com.ngon.backend.exception.ProductNotFoundException;
import com.ngon.backend.mapper.ResponseMapper;
import com.ngon.backend.product.Product;
import com.ngon.backend.product.ProductRepository;
import com.ngon.backend.user.User;
import com.ngon.backend.user.UserRepository;

@Service
public class FavoriteService {
    private final FavoriteRepository favoriteRepo;
    private final UserRepository userRepo;
    private final ProductRepository productRepo;
    private final ResponseMapper responseMapper;

    public FavoriteService(FavoriteRepository favoriteRepo, 
        UserRepository userRepo, 
        ProductRepository productRepo,
        ResponseMapper responseMapper)
    {
        this.favoriteRepo = favoriteRepo;
        this.userRepo = userRepo;
        this.productRepo = productRepo;
        this.responseMapper = responseMapper;
    }

    @Transactional
    public FavoriteResponse addFavorite(Long productId, Authentication auth)
    {
        User user = userRepo.findByUsername(auth.getName());
        Product product = productRepo.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Product not found"));

        if (favoriteRepo.existsByUserIdAndProductId(user.getId(), productId))
        {
            throw new FavoriteExistsException("Product already favorited");
        }
        
        Favorite favorite = new Favorite();
        favorite.setUser(user);
        favorite.setProduct(product);

        favoriteRepo.save(favorite);

        return responseMapper.toFavoriteResponse(favorite);
    }

    public List<Favorite> getUserFavorites(User user)
    {
        return favoriteRepo.findAllByUserId(user.getId());
    }
}
