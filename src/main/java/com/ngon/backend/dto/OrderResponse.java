package com.ngon.backend.dto;

import java.util.List;

public record OrderResponse(
        List<OrderItemResponse> orderItems
)
{}
