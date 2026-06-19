package com.ngon.backend.dto;

public record UpdateOrderItemRequest(
        Long id,
        int quantity
)
{}
