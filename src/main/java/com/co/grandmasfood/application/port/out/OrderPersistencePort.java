package com.co.grandmasfood.application.port.out;

import com.co.grandmasfood.domain.model.Order;
import reactor.core.publisher.Mono;

public interface OrderPersistencePort{
    Mono<Order> save(Order order);
    Mono<Order> findByUuid(String uuid);
    Mono<Void> deleteByUuid(String uuid);
}
