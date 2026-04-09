package com.co.grandmasfood.application.port.in.order;

import reactor.core.publisher.Mono;

public interface DeleteOrderUseCase {
    Mono<Void> deleteOrder(String uuid);
}
