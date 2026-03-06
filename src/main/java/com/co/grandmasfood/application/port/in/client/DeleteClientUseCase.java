package com.co.grandmasfood.application.port.in.client;

import com.co.grandmasfood.domain.model.Client;
import reactor.core.publisher.Mono;

public interface DeleteClientUseCase {
    Mono<Void> deleteClient(String document);
}
