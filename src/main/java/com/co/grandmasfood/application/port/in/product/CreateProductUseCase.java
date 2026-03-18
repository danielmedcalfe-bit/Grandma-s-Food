package com.co.grandmasfood.application.port.in.product;

import com.co.grandmasfood.domain.model.Product;
import reactor.core.publisher.Mono;

public interface CreateProductUseCase {
    Mono<Product> createProduct(CreateProductCommand command);
}
