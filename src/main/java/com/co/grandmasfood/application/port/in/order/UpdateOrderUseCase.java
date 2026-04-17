package com.co.grandmasfood.application.port.in.order;

import reactor.core.publisher.Mono;

public interface UpdateOrderUseCase {
    Mono<Void> updateByUuid(String uuid, UpdateOrderCommand command);
}
