package com.co.grandmasfood.application.port.in.product;

import com.co.grandmasfood.domain.model.Product;
import reactor.core.publisher.Mono;

public interface GetProductUseCase {
     Mono<Product> getProductByCode(String Code);
}
