package com.co.grandmasfood.infrastructure.adapter.out.persistence.adapter;

import com.co.grandmasfood.application.port.out.ClientPersistencePort;
import com.co.grandmasfood.domain.model.Client;
import com.co.grandmasfood.infrastructure.adapter.out.persistence.mapper.ClientEntityMapper;
import com.co.grandmasfood.infrastructure.adapter.out.persistence.repository.ClientR2dbcRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
@Slf4j
public class ClientPersistenceAdapter implements ClientPersistencePort {

    private final ClientR2dbcRepository r2dbcRepository;
    private final ClientEntityMapper entityMapper;

    @Override
    public Mono<Client> save(Client client) {
        log.debug("Saving client with document: {}", client.getDocument().getValue());

        return r2dbcRepository.findByDocument(client.getDocument().getValue())
                .flatMap(existingEntity -> {
                    log.debug("Client exists with id: {}, performing UPDATE", existingEntity.getId());
                    var entityToUpdate = entityMapper.toEntity(client);
                    entityToUpdate.setId(existingEntity.getId());
                    return r2dbcRepository.save(entityToUpdate);
                })
                .switchIfEmpty(
                        Mono.defer(() -> {
                            log.debug("Client doesn't exist, performing INSERT");

                            var entityToInsert = entityMapper.toEntity(client);

                            return r2dbcRepository.save(entityToInsert);
                        })
                )
                .map(entityMapper::toDomain)
                .doOnSuccess(savedClient ->
                        log.debug("Client saved successfully: {}", savedClient.getDocument().getValue())
                );
    }

    @Override
    public Mono<Boolean> existsByDocument(String document) {
        log.debug("Checking if client exists with document: {}", document);

        return r2dbcRepository.existsByDocument(document);
    }

    @Override
    public Mono<Client> findByDocument(String document) {
        log.debug("Finding client by document: {}", document);

        return r2dbcRepository.findByDocument(document)
                .map(entityMapper::toDomain);
    }


}