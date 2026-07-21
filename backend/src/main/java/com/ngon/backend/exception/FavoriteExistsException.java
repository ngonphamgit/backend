package com.ngon.backend.exception;

public class FavoriteExistsException extends RuntimeException
{
    public FavoriteExistsException(String message)
    {
        super(message);
    }
}
