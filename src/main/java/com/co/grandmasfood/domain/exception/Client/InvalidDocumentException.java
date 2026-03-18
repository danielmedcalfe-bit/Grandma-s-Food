package com.co.grandmasfood.domain.exception.Client;

public class InvalidDocumentException extends DomainException {

    public InvalidDocumentException(String document) {
        super("INVALID_DOCUMENT_FORMAT",
                "Invalid document format: " + document +
                        ". Expected format: TYPE-NUMBER (e.g., CC-123456)");
    }
}