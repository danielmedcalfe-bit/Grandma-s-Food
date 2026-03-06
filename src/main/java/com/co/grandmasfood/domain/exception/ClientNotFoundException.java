package com.co.grandmasfood.domain.exception;

public class ClientNotFoundException extends DomainException {
    // Esta clase representa el error "Cliente no encontrado"

    public ClientNotFoundException(String document) {
        // Constructor: recibe el número de documento que no se encontró

        super("CLIENT_NOT_FOUND",  // ← Código de error único
                "Client with document " + document + " not found");  // ← Mensaje
        // Ejemplo: "Client with document CC-123456 not found"
    }
}