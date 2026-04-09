package com.co.grandmasfood.application.port.in.order;

import com.co.grandmasfood.domain.model.Client;
import com.co.grandmasfood.domain.model.Order;
import reactor.core.publisher.Mono;

public interface GetOrderUseCase {
    Mono<Order> getOrderByUuid(String uuid);
}
