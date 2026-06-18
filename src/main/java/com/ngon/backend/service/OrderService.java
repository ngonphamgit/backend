package com.ngon.backend.service;

import com.ngon.backend.dto.OrderItemResponse;
import com.ngon.backend.dto.OrderResponse;
import com.ngon.backend.entity.Order;
import com.ngon.backend.entity.OrderItem;
import com.ngon.backend.entity.OrderStatus;
import com.ngon.backend.entity.User;
import com.ngon.backend.repository.OrderRepository;
import com.ngon.backend.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService
{
    private final OrderRepository orderRepo;
    private final UserRepository userRepo;

    public OrderService(OrderRepository orderRepo, UserRepository userRepo)
    {
        this.orderRepo = orderRepo;
        this.userRepo = userRepo;
    }

    @Transactional
    public OrderResponse getCurrentOrder(Authentication auth)
    {
        User user = userRepo.findByUsername(auth.getName());
        Order currentOrder = orderRepo.findByUserIdAndStatus(user.getId(), OrderStatus.CART)
                .orElseGet(() -> createOrder(user));

        return orderToResponse(currentOrder);
    }

    private Order createOrder(User user)
    {
        Order order = new Order();
        order.setUser(user);
        order.setOrderTime(LocalDateTime.now());
        order.setStatus(OrderStatus.CART);

        orderRepo.save(order);
        return order;
    }

    private OrderResponse orderToResponse(Order order)
    {
        List<OrderItemResponse> orderItemResponses = new ArrayList<>();
        for (OrderItem orderItem : order.getOrderItems())
        {
            orderItemResponses.add(orderItemToResponse(orderItem));
        }
        return new OrderResponse(orderItemResponses);
    }

    private OrderItemResponse orderItemToResponse(OrderItem item)
    {
        return new OrderItemResponse(
            item.getId(),
            item.getProduct().getName(),
            item.getQuantity(),
            item.getUnitPrice()
        );
    }
}
