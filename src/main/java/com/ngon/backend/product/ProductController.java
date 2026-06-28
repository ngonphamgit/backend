package com.ngon.backend.product;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://127.0.0.1:5500")
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
    public ProductResponse createProduct(@RequestBody CreateProductRequest request)
    {
        return productService.createProduct(request);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/update/{id}")
    public ProductResponse updateProduct(@PathVariable Long id, @RequestBody UpdateProductRequest request)
    {
        return productService.updateProduct(id, request);
    }

    @GetMapping("/{id}")
    public ProductResponse getProductById(@PathVariable Long id)
    {
        return productService.getProductById(id);
    }

    @GetMapping("/categories/{category}")
    public Page<ProductResponse> getProductsByCategory(@PathVariable String category, Pageable pageable)
    {
        return productService.getProductsByCategory(category, pageable);
    }

    @GetMapping("/search")
    public Page<ProductResponse> searchProducts(@RequestParam String query, Pageable pageable)
    {
        return productService.searchProducts(query, pageable);
    }
}
