package com.co.grandmasfood.domain.model;

import com.co.grandmasfood.domain.valueobject.DocumentNumber;
import com.co.grandmasfood.domain.valueobject.Email;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class Client {
    private DocumentNumber document;
    private String name;
    private Email email;
    private String phone;
    private String address;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public void validate() {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be empty");
        }
        if (name.length() > 255) {
            throw new IllegalArgumentException("Name cannot exceed 255 characters");
        }
        if (phone == null || !phone.matches("^[0-9]{10}$")) {
            throw new IllegalArgumentException("Phone must be exactly 10 digits");
        }
        if (address == null || address.trim().isEmpty()) {
            throw new IllegalArgumentException("Address cannot be empty");
        }
        if (address.length() > 500) {
            throw new IllegalArgumentException("Address cannot exceed 500 characters");
        }
    }
}