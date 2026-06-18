package com.ngon.backend.dto;

import java.math.BigDecimal;

public record OrderItemResponse(
        Long id,
        Long productId,
        String name,
        int quantity,
        BigDecimal unitPrice
)
{}
