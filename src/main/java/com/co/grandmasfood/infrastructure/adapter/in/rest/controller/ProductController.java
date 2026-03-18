package com.co.grandmasfood.infrastructure.adapter.in.rest.controller;

import com.co.grandmasfood.application.port.in.product.CreateProductUseCase;
import com.co.grandmasfood.infrastructure.adapter.in.rest.dto.ProductRequestDto;
import com.co.grandmasfood.infrastructure.adapter.in.rest.dto.ProductResponseDto;
import com.co.grandmasfood.infrastructure.adapter.in.rest.mapper.ProductDtoMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/products")
@Slf4j
@RequiredArgsConstructor
public class ProductController {

    private final CreateProductUseCase createProductUseCase;
    private final ProductDtoMapper dtoMapper;

    @PostMapping(
            consumes= MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<ProductResponseDto> createProduct(
            @Valid @RequestBody ProductRequestDto request){

        log.info("POST/Products - Creating products with number: {}", request.getCode());

        return  Mono.just(request)
                .map(dtoMapper::toCreateCommand)
                .flatMap(createProductUseCase::createProduct)
                .map(dtoMapper::toResponseDto)
                .doOnSuccess(response ->
                        log.info("Product created sucessfully: {}", response.getCode()));






    }
}
