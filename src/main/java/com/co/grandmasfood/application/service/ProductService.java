package com.co.grandmasfood.application.service;

import com.co.grandmasfood.application.port.in.product.CreateProductCommand;
import com.co.grandmasfood.application.port.in.product.CreateProductUseCase;
import com.co.grandmasfood.application.port.out.ProductPersistencePort;
import com.co.grandmasfood.application.port.out.ProductPersistencePort;
import com.co.grandmasfood.domain.exception.Product.ProductAlreadyExistsException;
import com.co.grandmasfood.domain.model.Product;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService implements CreateProductUseCase {

    private final ProductPersistencePort productPersistencePort;

    @Override
    public Mono<Product> createProduct(CreateProductCommand command) {
        log.info("Create product with code: {}",command.getCode());
        return productPersistencePort.existsByCode(command.getCode())
                .flatMap(exists ->{
                    if(exists){
                        log.warn("Produc with code {} already exists",command.getCode());
                        return Mono.error( new ProductAlreadyExistsException(command.getCode()));
                    }
                Product product=buildProduct(command);

                 return productPersistencePort.save(product)
                         .doOnSuccess(savedProduct ->
                                 log.info("Product created correctly with code: {}", savedProduct.getCode()));

                });
    }

    private Product buildProduct(CreateProductCommand command){

        return Product.builder()
                .code(command.getCode())
                .name(command.getName())
                .price(command.getPrice())
                .stock(command.getStock())
                .description(command.getDescription())
                .category(command.getCategory())
                .build();


    }


}
