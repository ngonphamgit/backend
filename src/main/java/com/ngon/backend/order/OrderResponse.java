package com.ngon.backend.order;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record OrderResponse(
        Long orderId,
        OrderStatus status,
        LocalDateTime orderTime,
        BigDecimal total,
        List<OrderItemResponse> orderItems
)
{}
