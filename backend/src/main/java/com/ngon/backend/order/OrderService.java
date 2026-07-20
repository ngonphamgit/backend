package com.ngon.backend.order;

import com.ngon.backend.exception.*;
import com.ngon.backend.product.Product;
import com.ngon.backend.product.ProductRepository;
import com.ngon.backend.user.UserRepository;
import com.ngon.backend.user.User;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
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
                .orElseThrow(() -> new ProductNotFoundException("Product not found"));

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
                .orElseThrow(() -> new OrderItemNotFoundException("Order item not found"));

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
                .orElseThrow(() -> new OrderItemNotFoundException("Order item not found"));

        BigDecimal beforeSubtotal = orderItem.getUnitPrice().multiply(BigDecimal.valueOf(orderItem.getQuantity()));
        currentOrder.setTotal(currentOrder.getTotal().subtract(beforeSubtotal));

        orderItem.setQuantity(request.quantity());

        BigDecimal afterSubtotal = orderItem.getUnitPrice().multiply(BigDecimal.valueOf(orderItem.getQuantity()));
        currentOrder.setTotal(currentOrder.getTotal().add(afterSubtotal));

        orderRepo.save(currentOrder);

        return orderToResponse(currentOrder);
    }

    @Transactional
    public CheckoutResponse checkout(Authentication auth)
    {
        User user = userRepo.findByUsername(auth.getName());
        Order currentOrder = orderRepo.findByUserIdAndStatus(user.getId(), OrderStatus.CART)
                .orElseThrow(() -> new NoActiveCartException("No active cart"));

        List<OrderItem> orderItems = currentOrder.getOrderItems();
        if (orderItems.isEmpty())
        {
            throw new EmptyCartException("Cart is empty");
        }

        BigDecimal total = BigDecimal.ZERO;
        for (OrderItem orderItem : orderItems)
        {
            Product product = productRepo.findById(orderItem.getProduct().getId())
                    .orElseThrow(() -> new ProductNotFoundException("Product no longer exists"));

            if (orderItem.getQuantity() > product.getQuantity())
            {
                throw new InsufficientStockException("Insufficient stock");
            }

            product.setQuantity(product.getQuantity() - orderItem.getQuantity());

            BigDecimal subtotal = orderItem.getUnitPrice().multiply(BigDecimal.valueOf(orderItem.getQuantity()));
            total = total.add(subtotal);
        }

        currentOrder.setTotal(total);
        currentOrder.setStatus(OrderStatus.PLACED);

        return orderToCheckoutResponse(currentOrder);
    }

    public List<Order> getUserOrders(User user)
    {
        return orderRepo.findAllByUserId(user.getId());
    }

    private Order createOrder(User user)
    {
        Order order = new Order();
        order.setUser(user);
        order.setOrderTime(LocalDateTime.now());
        order.setTotal(BigDecimal.ZERO);
        order.setStatus(OrderStatus.CART);

        orderRepo.save(order);
        return order;
    }

    public OrderResponse orderToResponse(Order order)
    {
        List<OrderItemResponse> orderItemResponses = new ArrayList<>();
        for (OrderItem orderItem : order.getOrderItems())
        {
            orderItemResponses.add(orderItemToResponse(orderItem));
        }
        return new OrderResponse(order.getId(), order.getStatus(), order.getOrderTime(), order.getTotal(), orderItemResponses);
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

    private CheckoutResponse orderToCheckoutResponse(Order order)
    {
        List<OrderItemResponse> orderItemResponses = new ArrayList<>();
        for (OrderItem orderItem : order.getOrderItems())
        {
            orderItemResponses.add(orderItemToResponse(orderItem));
        }
        return new CheckoutResponse(
                order.getId(),
                order.getTotal(),
                LocalDateTime.now(),
                orderItemResponses
        );
    }
}
