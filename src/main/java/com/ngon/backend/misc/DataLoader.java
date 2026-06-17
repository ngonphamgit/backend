package com.ngon.backend.misc;

import com.ngon.backend.entity.Product;
import com.ngon.backend.entity.Role;
import com.ngon.backend.entity.User;
import com.ngon.backend.repository.ProductRepository;
import com.ngon.backend.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

@Component
public class DataLoader implements CommandLineRunner
{
    private final UserRepository userRepo;
    private final ProductRepository productRepo;
    private final PasswordEncoder passwordEncoder;

    public DataLoader(UserRepository userRepo, ProductRepository productRepo, PasswordEncoder passwordEncoder)
    {
        this.userRepo = userRepo;
        this.productRepo = productRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args)
    {
        if (userRepo.count() <= 1)
        {
            for (int i = 0; i < 50; i++)
            {
                User user = new User();
                user.setUsername("user" + i);
                user.setEmail(user.getUsername() + "@gmail.com");
                user.setPassword(passwordEncoder.encode("password" + i));
                user.setRole(Role.ROLE_USER);

                userRepo.save(user);
            }
        }

        if (productRepo.count() <= 0)
        {
            for (int i = 0; i < 100; i++)
            {
                Product product = new Product();
                product.setName("product" + i);
                product.setQuantity(ThreadLocalRandom.current().nextInt(100) + 1);
                product.setPrice(Math.round(ThreadLocalRandom.current().nextFloat(1.0f, 1000.0f) * 100) / 100.0f);
                product.setCategory("category" + ThreadLocalRandom.current().nextInt(1, 8));

                System.out.println(product.getName() + " " + product.getQuantity() + " " + product.getPrice() + " " + product.getCategory());
                productRepo.save(product);
            }
        }
    }
}
