package com.ngon.backend.order;

import java.util.List;

public record OrderResponse(
        List<OrderItemResponse> orderItems
)
{}
