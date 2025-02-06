package com.example.user_service.exception;

public class CacheException extends  RuntimeException {
    public CacheException() {
    }

    public CacheException(String message) {
        super(message);
    }
}
