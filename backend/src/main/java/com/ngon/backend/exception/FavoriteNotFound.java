package com.ngon.backend.exception;

public class FavoriteNotFound extends RuntimeException 
{
    public FavoriteNotFound(String message)
    {
        super(message);
    }
}
