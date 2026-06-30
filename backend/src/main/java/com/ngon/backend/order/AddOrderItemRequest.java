package com.ngon.backend.order;

public record AddOrderItemRequest(
        Long id,
        int quantity
)
{}
