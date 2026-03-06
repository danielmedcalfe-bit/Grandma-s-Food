package com.co.grandmasfood.domain.valueobject;

import com.co.grandmasfood.domain.exception.InvalidDocumentException;
import lombok.Value;

@Value
public class DocumentNumber {
    String value;

    public DocumentNumber(String value) {
        if (!isValid(value)) {
            throw new InvalidDocumentException(value);
        }
        this.value = value;
    }

    private boolean isValid(String value) {
        if (value == null || value.trim().isEmpty()) {
            return false;
        }

        String pattern = "^(CC|CE|P|TI|NIT|PPT)-[0-9]{1,15}$";
        return value.matches(pattern) && value.length() <= 20;
    }



}