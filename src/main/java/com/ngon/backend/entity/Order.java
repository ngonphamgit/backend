package com.ngon.backend.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
public class Order
{
    @Id
    @GeneratedValue
    private Long id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    private LocalDateTime orderTime;
    @Enumerated(EnumType.STRING)
    private OrderStatus status;
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> items = new ArrayList<OrderItem>();

    public void setUser(User user) {this.user = user;}
    public void setOrderTime(LocalDateTime orderTime) {this.orderTime = orderTime;}
    public void setStatus(OrderStatus status) {this.status = status;}
    public void addOrderItem(OrderItem item)
    {
        this.items.add(item);
        item.setOrder(this);
    }
    public void removeOrderItem(OrderItem item)
    {
        this.items.remove(item);
        item.setOrder(null);
    }

    public User getUser() {return this.user;}
    public LocalDateTime getOrderTime() {return this.orderTime;}
    public OrderStatus getStatus() {return this.status;}
    public List<OrderItem> getOrderItems() {return this.items;}
}
