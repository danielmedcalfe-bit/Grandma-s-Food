package com.co.grandmasfood.infrastructure.adapter.out.persistence.repository;

import com.co.grandmasfood.infrastructure.adapter.out.persistence.entity.ClientEntity;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface ClientR2dbcRepository extends R2dbcRepository<ClientEntity, Long> {

    Mono<Boolean> existsByDocument(String document);
    Mono<ClientEntity> findByDocument(String document);
}