package com.co.grandmasfood.infrastructure.adapter.in.rest.controller;

import com.co.grandmasfood.application.port.in.product.CreateProductUseCase;
import com.co.grandmasfood.application.port.in.product.GetProductUseCase;
import com.co.grandmasfood.application.port.in.product.UpdateProductUseCase;
import com.co.grandmasfood.domain.model.Product;
import com.co.grandmasfood.infrastructure.adapter.in.rest.dto.ProductRequestDto;
import com.co.grandmasfood.infrastructure.adapter.in.rest.dto.ProductResponseDto;
import com.co.grandmasfood.infrastructure.adapter.in.rest.dto.UpdateProductResquestDto;
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
    private final GetProductUseCase getProductUseCase;
    private final UpdateProductUseCase updateProductUseCase;
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

    @GetMapping(
            value = "/{code}" ,
            produces=MediaType.APPLICATION_JSON_VALUE
    )
    public Mono<ProductResponseDto> getProduct(@PathVariable String code) {
        log.info("GET/get products/{}", code);
        return getProductUseCase.getProductByCode(code)
                .map(dtoMapper::toResponseDto)
                .doOnSuccess(response ->
                        log.info("Retrieving Product with code: {}", response.getCode())
                );

    }

    @PutMapping(
            value = "/{code}",
            consumes =MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> putProduct(@PathVariable String code,
                                               @Valid @RequestBody UpdateProductResquestDto requestDto){
        log.info("Updatind product by code: {}", code);
        return Mono.just(requestDto)
                .map(dtoMapper::updateCommand)
                .flatMap(command ->
                        updateProductUseCase.updateByCode(code, command))
                .doOnSuccess(v ->
                        log.info("updating successfully: {}", code));





    }
}
