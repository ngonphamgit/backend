package com.ngon.backend.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
public class Order
{
    @Id
    @GeneratedValue
    private Long id;
    @ManyToOne
    private User user;
    private LocalDateTime orderTime;
    @Enumerated(EnumType.STRING)
    private OrderStatus status;
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> items;
}
