package com.ngon.backend.order;

import java.math.BigDecimal;

public record OrderItemResponse(
        Long id,
        Long productId,
        String name,
        int quantity,
        BigDecimal unitPrice
)
{}
