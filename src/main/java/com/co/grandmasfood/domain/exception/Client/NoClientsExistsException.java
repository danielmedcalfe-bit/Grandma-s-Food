package com.co.grandmasfood.domain.exception.Client;

public class NoClientsExistsException extends DomainException {

    public NoClientsExistsException(String errorCode ) {
        super("NO_EXISTING_CLIENTS", "There is not existing clients" );
    }
}
