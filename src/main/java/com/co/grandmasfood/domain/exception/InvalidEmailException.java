package com.co.grandmasfood.domain.exception;

public class InvalidEmailException extends DomainException {

    public InvalidEmailException(String email) {
        super("INVALID_EMAIL_FORMAT",
                "Invalid email format: " + email);
    }
}