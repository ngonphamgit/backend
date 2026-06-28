package com.ngon.backend.misc;

import com.ngon.backend.product.Product;
import com.ngon.backend.user.Role;
import com.ngon.backend.user.User;
import com.ngon.backend.product.ProductRepository;
import com.ngon.backend.user.UserRepository;
import net.datafaker.Faker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
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
        Faker faker = new Faker();

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
            for (int i = 0; i < 1000; i++)
            {
                Product product = new Product();
                product.setName(faker.commerce().productName());
                product.setQuantity(ThreadLocalRandom.current().nextInt(100) + 1);
                product.setPrice(BigDecimal.valueOf(ThreadLocalRandom.current().nextInt(100, 100000), 2));
                product.setCategory("category" + ThreadLocalRandom.current().nextInt(1, 8));
                product.setDescription(faker.lorem().sentence());

                productRepo.save(product);
            }
        }
    }
}
