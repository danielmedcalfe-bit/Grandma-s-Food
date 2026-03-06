package com.co.grandmasfood.application.service;

import com.co.grandmasfood.application.port.in.client.*;
import com.co.grandmasfood.application.port.out.ClientPersistencePort;
import com.co.grandmasfood.domain.exception.ClientAlreadyExistsException;
import com.co.grandmasfood.domain.exception.ClientNotFoundException;
import com.co.grandmasfood.domain.exception.NoChangesDetectedException;
import com.co.grandmasfood.domain.exception.NoClientsExistsException;
import com.co.grandmasfood.domain.model.Client;
import com.co.grandmasfood.infrastructure.adapter.in.rest.dto.ClientResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ClientService implements CreateClientUseCase, GetClientUseCase, UpdateClientUseCase,DeleteClientUseCase {

    private final ClientPersistencePort persistencePort;

    @Override
    public Mono<Client> createClient(CreateClientCommand command) {
        log.info("Creating client with document: {}", command.getDocument().getValue());

        return persistencePort.existsByDocument(command.getDocument().getValue())
                .flatMap(exists -> {
                    if (exists) {
                        log.warn("Client with document {} already exists",
                                command.getDocument().getValue());
                        return Mono.error(
                                new ClientAlreadyExistsException(command.getDocument().getValue())
                        );
                    }

                    Client client = buildClient(command);
                    client.validate();

                    return persistencePort.save(client)
                            .doOnSuccess(savedClient ->
                                    log.info("Client created successfully with document: {}",
                                            savedClient.getDocument().getValue())
                            );
                });
    }



    @Override
    public Mono<Client> getClientByDocument(String document) {

        log.info("Retrieving client with document: {}", document);

        return persistencePort.findByDocument(document)
                .switchIfEmpty(Mono.error(new ClientNotFoundException(document)))
                .doOnSuccess(client ->
                        log.info("Client found: {}", client.getDocument().getValue())
                );
    }

    @Override
    public Mono<Void> updateClient(String document, UpdateClientCommand command) {

        log.info("Updating client with document: {}", document);

        if (!command.hasChanges()) {

            log.warn("No changes detected in update request for document: {}", document);

            return Mono.error(new NoChangesDetectedException(
                    "No changes detected in the request"
            ));
        }


        return persistencePort.findByDocument(document)
                .switchIfEmpty(Mono.error(new ClientNotFoundException(document)))

                .flatMap(existingClient -> {

                    Client updatedClient = applyUpdates(existingClient, command);

                    if (!hasActualChanges(existingClient, updatedClient)) {

                        log.warn("No actual changes detected for document: {}", document);
                        return Mono.error(new NoChangesDetectedException(
                                "No changes detected in the request"
                        ));

                    }
                    updatedClient.validate();

                    updatedClient.setUpdatedAt(LocalDateTime.now());

                    return persistencePort.save(updatedClient);

                })
                .then()

                .doOnSuccess(v ->
                        log.info("Client updated successfully: {}", document)
                );

    }
    @Override
    public Mono<Void> deleteClient(String document) {
        log.info("Deleting client by document: {}", document);
        return persistencePort.findByDocument(document)
                .switchIfEmpty(Mono.error(new ClientNotFoundException(document)))
                .flatMap(Client -> persistencePort.deleteByDocument(document))
                .doOnSuccess(v -> log.info("Client deleted Successfully: {}", document));

    }




    private Client applyUpdates(Client existing, UpdateClientCommand command) {

        return Client.builder()
                .document(existing.getDocument())
                .name(command.getName() != null ? command.getName() : existing.getName())
                .email(command.getEmail() != null ? command.getEmail() : existing.getEmail())
                .phone(command.getPhone() != null ? command.getPhone() : existing.getPhone())
                .address(command.getAddress() != null ? command.getAddress() : existing.getAddress())
                .createdAt(existing.getCreatedAt())
                .updatedAt(existing.getUpdatedAt())
                .build();

    }

    private boolean hasActualChanges(Client existing, Client updated) {

        return !existing.getName().equals(updated.getName()) ||
                !existing.getEmail().equals(updated.getEmail()) ||
                !existing.getPhone().equals(updated.getPhone()) ||
                !existing.getAddress().equals(updated.getAddress());


    }


    private Client buildClient(CreateClientCommand command) {
        LocalDateTime now = LocalDateTime.now();

        return Client.builder()
                .document(command.getDocument())
                .name(command.getName())
                .email(command.getEmail())
                .phone(command.getPhone())
                .address(command.getAddress())
                .createdAt(now)
                .updatedAt(now)
                .build();
    }



}