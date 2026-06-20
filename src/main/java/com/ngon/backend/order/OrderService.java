package com.ngon.backend.order;

import com.ngon.backend.product.Product;
import com.ngon.backend.product.ProductRepository;
import com.ngon.backend.user.UserRepository;
import com.ngon.backend.user.User;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService
{
    private final OrderItemRepository orderItemRepo;
    private final OrderRepository orderRepo;
    private final UserRepository userRepo;
    private final ProductRepository productRepo;

    public OrderService(OrderItemRepository orderItemRepo, OrderRepository orderRepo, UserRepository userRepo, ProductRepository productRepo)
    {
        this.orderItemRepo = orderItemRepo;
        this.orderRepo = orderRepo;
        this.userRepo = userRepo;
        this.productRepo = productRepo;
    }

    @Transactional
    public OrderResponse getCurrentOrder(Authentication auth)
    {
        User user = userRepo.findByUsername(auth.getName());
        Order currentOrder = orderRepo.findByUserIdAndStatus(user.getId(), OrderStatus.CART)
                .orElseGet(() -> createOrder(user));

        return orderToResponse(currentOrder);
    }

    @Transactional
    public OrderResponse addItemToCart(AddOrderItemRequest request, Authentication auth)
    {
        User user = userRepo.findByUsername(auth.getName());
        Order currentOrder = orderRepo.findByUserIdAndStatus(user.getId(), OrderStatus.CART)
                .orElseGet(() -> createOrder(user));

        Product product = productRepo.findById(request.id())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        OrderItem orderItem = new OrderItem();
        orderItem.setOrder(currentOrder);
        orderItem.setProduct(product);
        orderItem.setQuantity(request.quantity());
        orderItem.setUnitPrice(product.getPrice());

        currentOrder.addOrderItem(orderItem);
        orderRepo.save(currentOrder);

        return orderToResponse(currentOrder);
    }

    @Transactional
    public OrderResponse removeItemFromCart(RemoveOrderItemRequest request, Authentication auth)
    {
        User user = userRepo.findByUsername(auth.getName());
        Order currentOrder = orderRepo.findByUserIdAndStatus(user.getId(), OrderStatus.CART)
                .orElseGet(() -> createOrder(user));

        OrderItem orderItem = orderItemRepo.findById(request.id())
                .orElseThrow(() -> new RuntimeException("Order item not found"));

        currentOrder.removeOrderItem(orderItem);
        orderRepo.save(currentOrder);

        return orderToResponse(currentOrder);
    }

    @Transactional
    public OrderResponse updateItemInCart(UpdateOrderItemRequest request, Authentication auth)
    {
        User user = userRepo.findByUsername(auth.getName());
        Order currentOrder = orderRepo.findByUserIdAndStatus(user.getId(), OrderStatus.CART)
                .orElseGet(() -> createOrder(user));

        OrderItem orderItem = orderItemRepo.findById(request.id())
                .orElseThrow(() -> new RuntimeException("Order item not found"));

        orderItem.setQuantity(request.quantity());
        orderRepo.save(currentOrder);

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
        return new OrderResponse(order.getId(), order.getStatus(), order.getOrderTime(), orderItemResponses);
    }

    private OrderItemResponse orderItemToResponse(OrderItem item)
    {
        return new OrderItemResponse(
            item.getId(),
            item.getProduct().getId(),
            item.getProduct().getName(),
            item.getQuantity(),
            item.getUnitPrice()
        );
    }
}
