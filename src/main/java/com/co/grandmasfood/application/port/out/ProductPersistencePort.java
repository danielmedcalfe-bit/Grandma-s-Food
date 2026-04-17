package com.co.grandmasfood.application.port.out;

import com.co.grandmasfood.domain.model.Product;
import reactor.core.publisher.Mono;

public interface ProductPersistencePort {
    Mono<Product> save(Product product);
    Mono<Boolean> existsByCode(String code);
    Mono<Product> findByCode(String code);
    Mono<Void>  deleteByCode(String code);
}
