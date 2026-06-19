package com.ngon.backend.order;

public record UpdateOrderItemRequest(
        Long id,
        int quantity
)
{}
