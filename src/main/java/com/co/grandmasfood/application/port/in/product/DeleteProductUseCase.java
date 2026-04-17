package com.co.grandmasfood.application.port.in.product;

import reactor.core.publisher.Mono;

public interface DeleteProductUseCase {
    Mono<Void> deleteProduct(String code);
}
