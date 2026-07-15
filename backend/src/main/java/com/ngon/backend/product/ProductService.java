package com.ngon.backend.product;

import com.ngon.backend.exception.ProductNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
        Product product = productRepo.findById(id).orElseThrow(() -> new ProductNotFoundException("Product not found"));

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
                .orElseThrow(() -> new ProductNotFoundException("Product not found"));

        product.setName(request.name());
        product.setQuantity(request.quantity());
        product.setPrice(request.price());
        product.setCategory(request.category());

        productRepo.save(product);

        return toResponse(product);
    }

    public Page<ProductResponse> getProductsByCategory(String category, Pageable pageable)
    {
        return productRepo.findAllByCategory(category, pageable).map(this::toResponse);
    }

    public Page<ProductResponse> searchProducts(String query, Pageable pageable)
    {
        return productRepo.searchByName(query, pageable).map(this::toResponse);
    }

    private ProductResponse toResponse(Product product)
    {
        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getQuantity(),
                product.getPrice(),
                product.getCategory(),
                product.getDescription(),
                product.getProductType().toString()
        );
    }
}
