package com.co.grandmasfood.domain.exception.Client;

public class ClientAlreadyExistsException extends DomainException {

    public ClientAlreadyExistsException(String document) {
        super("CLIENT_ALREADY_EXISTS",
                "Client with document " + document + " already exists");
    }
}