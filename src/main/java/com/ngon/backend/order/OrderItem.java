package com.ngon.backend.order;

import com.ngon.backend.product.Product;
import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "order_items")
public class OrderItem
{
    @Id
    @GeneratedValue
    private Long id;
    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;
    @ManyToOne
    private Product product;
    private int quantity;
    private BigDecimal unitPrice;

    public void setOrder(Order order) {this.order = order;}
    public void setProduct(Product product) {this.product = product;}
    public void setQuantity(int quantity) {this.quantity = quantity;}
    public void setUnitPrice(BigDecimal unitPrice) {this.unitPrice = unitPrice;}

    public Long getId() {return this.id;}
    public Order getOrder() {return this.order;}
    public Product getProduct() {return this.product;}
    public int getQuantity() {return this.quantity;}
    public BigDecimal getUnitPrice() {return this.unitPrice;}
}
