package com.co.grandmasfood.infrastructure.adapter.in.rest.controller;

import com.co.grandmasfood.application.port.in.client.CreateClientUseCase;
import com.co.grandmasfood.application.port.in.client.DeleteClientUseCase;
import com.co.grandmasfood.application.port.in.client.GetClientUseCase;
import com.co.grandmasfood.application.port.in.client.UpdateClientUseCase;
import com.co.grandmasfood.infrastructure.adapter.in.rest.dto.ClientRequestDto;
import com.co.grandmasfood.infrastructure.adapter.in.rest.dto.ClientResponseDto;
import com.co.grandmasfood.infrastructure.adapter.in.rest.dto.UpdateClientRequestDto;
import com.co.grandmasfood.infrastructure.adapter.in.rest.mapper.ClientDtoMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import jakarta.validation.Valid;

import java.security.PublicKey;
import java.util.List;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/clients")
@RequiredArgsConstructor
@Slf4j
public class ClientController {

    private final CreateClientUseCase createClientUseCase;
    private final GetClientUseCase getClientUseCase;
    private final UpdateClientUseCase updateClientUseCase;
    private final DeleteClientUseCase deleteClientUseCase;
    private final ClientDtoMapper dtoMapper;

    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<ClientResponseDto> createClient(
            @Valid @RequestBody ClientRequestDto request) {

        log.info("POST /clients - Creating client with document: {}", request.getDocument());

        return Mono.just(request)
                .map(dtoMapper::toCreateCommand)
                .flatMap(createClientUseCase::createClient)
                .map(dtoMapper::toResponseDto)
                .doOnSuccess(response ->
                        log.info("Client created successfully: {}", response.getDocument())
                );


    }


    @GetMapping(
            value = "/{document}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public Mono<ClientResponseDto> getClient(@PathVariable String document) {

        log.info("GET /clients/{} - Retrieving client", document);

        return getClientUseCase.getClientByDocument(document)
                .map(dtoMapper::toResponseDto)
                .doOnSuccess(response ->
                        log.info("Client retrieved successfully: {}", response.getDocument())
                );
    }

    @PutMapping(
            value = "/{document}",
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> updateClient(
            @PathVariable String document,
            @Valid @RequestBody UpdateClientRequestDto request) {
        log.info("PUT /clients/{} - Updating client", document);
        return Mono.just(request)
                .map(dtoMapper::toUpdateCommand)
                .flatMap(command -> updateClientUseCase.updateClient(document, command))
                .doOnSuccess(v ->
                        log.info("Client updated successfully: {}", document)
                );

    }
    @DeleteMapping("/{document}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> deleteClient(@PathVariable String document){
        log.info("Delete/clients/{} -Deleting Client", document);
        return deleteClientUseCase.deleteClient(document)
                .doOnSuccess(  V->
                        log.info("Client deleted sucessfully: {}",document));

    }








}