package com.ngon.backend.exception;

public class OrderItemNotFoundException extends RuntimeException {
    public OrderItemNotFoundException(String message) {
        super(message);
    }
}
