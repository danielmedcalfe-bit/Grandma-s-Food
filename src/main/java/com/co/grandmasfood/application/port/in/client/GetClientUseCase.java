package com.co.grandmasfood.application.port.in.client;

import com.co.grandmasfood.domain.model.Client;
import reactor.core.publisher.Mono;

public interface GetClientUseCase {


    Mono<Client> getClientByDocument(String document);

}