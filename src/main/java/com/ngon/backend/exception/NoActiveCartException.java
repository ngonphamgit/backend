package com.ngon.backend.exception;

public class NoActiveCartException extends RuntimeException {
    public NoActiveCartException(String message) {
        super(message);
    }
}
