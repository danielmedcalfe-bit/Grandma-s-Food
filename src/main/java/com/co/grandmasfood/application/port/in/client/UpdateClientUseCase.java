package com.co.grandmasfood.application.port.in.client;

import reactor.core.publisher.Mono;

public interface UpdateClientUseCase {
    Mono<Void> updateClient(String document, UpdateClientCommand command);
}