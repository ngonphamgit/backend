package com.ngon.backend.product;

import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
public class ProductController
{
    private final ProductService productService;

    public ProductController(ProductService productService)
    {
        this.productService = productService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/create")
    public ProductResponse createProduct(@RequestBody ProductRequest request)
    {
        return productService.createProduct(request);
    }



    @GetMapping("/{id}")
    public ProductResponse getProductById(@PathVariable Long id)
    {
        return productService.getProductById(id);
    }

    @GetMapping("/categories/{category}")
    public Page<ProductResponse> getProductsByCategory(@PathVariable String category)
    {
        return productService.getProductsByCategory(category);
    }

}
