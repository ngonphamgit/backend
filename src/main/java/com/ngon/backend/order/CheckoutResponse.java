package com.ngon.backend.order;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record CheckoutResponse(
       Long id,
       BigDecimal total,
       LocalDateTime checkoutTime,
       List<OrderItemResponse> orderItems
)
{}
