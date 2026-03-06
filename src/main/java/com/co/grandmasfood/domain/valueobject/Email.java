package com.co.grandmasfood.domain.valueobject;

import com.co.grandmasfood.domain.exception.InvalidEmailException;
import lombok.Value;

@Value
public class Email {
    String value;

    public Email(String value) {
        if (!isValid(value)) {
            throw new InvalidEmailException(value);
        }
        this.value = value.toLowerCase();
    }

    private boolean isValid(String value) {
        if (value == null || value.trim().isEmpty()) {
            return false;
        }

        String pattern = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        return value.matches(pattern) && value.length() <= 255;
    }
}