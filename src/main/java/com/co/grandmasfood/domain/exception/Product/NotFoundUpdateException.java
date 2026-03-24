package com.co.grandmasfood.domain.exception.Product;

public class NotFoundUpdateException extends RuntimeException {
    public NotFoundUpdateException(String message) {
        super(message);
    }
}
