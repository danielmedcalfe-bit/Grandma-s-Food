package com.co.grandmasfood.application.port.in.client;

import com.co.grandmasfood.domain.model.Client;
import com.co.grandmasfood.infrastructure.adapter.in.rest.dto.ClientResponseDto;
import reactor.core.publisher.Mono;

import java.util.List;

public interface CreateClientUseCase {
    Mono<Client> createClient(CreateClientCommand command);

}