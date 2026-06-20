package com.ngon.backend.product;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductService
{
    private final ProductRepository productRepo;

    public ProductService(ProductRepository productRepo)
    {
        this.productRepo = productRepo;
    }

    public ProductResponse getProductById(Long id)
    {
        Product product = productRepo.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));

        return this.toResponse(product);
    }

    @Transactional
    public ProductResponse createProduct(CreateProductRequest request)
    {
        Product product = new Product();
        product.setName(request.name());
        product.setQuantity(request.quantity());
        product.setPrice(request.price());
        product.setCategory(request.category());

        productRepo.save(product);

        return toResponse(product);
    }

    @Transactional
    public ProductResponse updateProduct(Long id, UpdateProductRequest request)
    {
        Product product = productRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        product.setName(request.name());
        product.setQuantity(request.quantity());
        product.setPrice(request.price());
        product.setCategory(request.category());

        productRepo.save(product);

        return toResponse(product);
    }

    public Page<ProductResponse> getProductsByCategory(String category)
    {
        return productRepo.findAllByCategory(category, PageRequest.of(0, 20)).map(this::toResponse);
    }

    private ProductResponse toResponse(Product product)
    {
        return new ProductResponse(
                product.getName(),
                product.getQuantity(),
                product.getPrice(),
                product.getCategory()
        );
    }
}
