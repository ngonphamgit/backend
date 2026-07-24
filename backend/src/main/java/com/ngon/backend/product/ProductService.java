package com.ngon.backend.product;

import com.ngon.backend.exception.ProductNotFoundException;
import com.ngon.backend.mapper.ResponseMapper;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductService
{
    private final ProductRepository productRepo;
    private final ResponseMapper responseMapper;

    public ProductService(ProductRepository productRepo, ResponseMapper responseMapper)
    {
        this.productRepo = productRepo;
        this.responseMapper = responseMapper;
    }

    public ProductResponse getProductById(Long id)
    {
        Product product = productRepo.findById(id).orElseThrow(() -> new ProductNotFoundException("Product not found"));

        return responseMapper.toProductResponse(product);
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

        return responseMapper.toProductResponse(product);
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

        return responseMapper.toProductResponse(product);
    }

    public Page<ProductResponse> getProductsByCategory(String category, Pageable pageable)
    {
        return productRepo.findAllByCategory(category, pageable).map(responseMapper::toProductResponse);
    }

    public Page<ProductResponse> getProductsByType(String type)
    {
        return productRepo.findAllByProductType(ProductType.valueOf(type), PageRequest.of(0, 5)).map(responseMapper::toProductResponse);
    }

    public Page<ProductResponse> searchProducts(String query, Pageable pageable)
    {
        return productRepo.searchByName(query, pageable).map(responseMapper::toProductResponse);
    }
}
