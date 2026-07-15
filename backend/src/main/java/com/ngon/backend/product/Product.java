package com.ngon.backend.product;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "products")
public class Product
{
    @Id
    @GeneratedValue
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private int quantity;
    @Column(nullable = false)
    private BigDecimal price;
    @Column(nullable = false)
    private String category;
    @Column(nullable = false)
    private String description;
    @Enumerated(EnumType.STRING)
    private ProductType productType;

    public void setName(String name) {this.name = name;}
    public void setQuantity(int quantity) {this.quantity = quantity;}
    public void setPrice(BigDecimal price) {this.price = price;}
    public void setCategory(String category) {this.category = category;}
    public void setDescription(String desc) {this.description = desc;}
    public void setProductType(ProductType type) {this.productType = type;}

    public Long getId() {return this.id;}
    public String getName() {return this.name;}
    public int getQuantity() {return this.quantity;}
    public BigDecimal getPrice() {return this.price;}
    public String getCategory() {return this.category;}
    public String getDescription() {return this.description;}
    public ProductType getProductType() {return this.productType;}
}
