package com.ngon.backend.entity;

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

    public void setName(String name) {this.name = name;}
    public void setQuantity(int quantity) {this.quantity = quantity;}
    public void setPrice(BigDecimal price) {this.price = price;}
    public void setCategory(String category) {this.category = category;}

    public Long getId() {return this.id;}
    public String getName() {return this.name;}
    public int getQuantity() {return this.quantity;}
    public BigDecimal getPrice() {return this.price;}
    public String getCategory() {return this.category;}
}
