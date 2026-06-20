package com.ngon.backend.order;

import java.time.LocalDateTime;
import java.util.List;

public record OrderResponse(
        Long orderId,
        OrderStatus status,
        LocalDateTime orderTime,
        List<OrderItemResponse> orderItems
)
{}
