package com.co.grandmasfood.application.port.out;

import com.co.grandmasfood.domain.model.Client;
import com.co.grandmasfood.infrastructure.adapter.in.rest.dto.ClientResponseDto;
import com.co.grandmasfood.infrastructure.adapter.out.persistence.entity.ClientEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public interface ClientPersistencePort {
    Mono<Client> save(Client client);
    Mono<Boolean> existsByDocument(String document);
    Mono<Client> findByDocument(String document);
    Mono<Void>  deleteByDocument(String document);
}