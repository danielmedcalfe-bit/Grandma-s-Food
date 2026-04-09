package com.co.grandmasfood.infrastructure.adapter.in.rest.controller;

import com.co.grandmasfood.application.port.in.order.CreateOrderUseCase;
import com.co.grandmasfood.application.port.in.order.DeleteOrderUseCase;
import com.co.grandmasfood.application.port.in.order.GetOrderUseCase;
import com.co.grandmasfood.infrastructure.adapter.in.rest.dto.ClientResponseDto;
import com.co.grandmasfood.infrastructure.adapter.in.rest.dto.OrderRequestDto;
import com.co.grandmasfood.infrastructure.adapter.in.rest.dto.OrderResponseDto;
import com.co.grandmasfood.infrastructure.adapter.in.rest.mapper.OrderDtoMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RequestMapping("/orders")
@RequiredArgsConstructor
@RestController
@Slf4j
public class OrderController {

    private final GetOrderUseCase getOrderUseCase;
    private final DeleteOrderUseCase deleteOrderUseCase;
    private final CreateOrderUseCase createOrderUseCase;
    private final OrderDtoMapper orderDtoMapper;


    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<OrderResponseDto> createOrder(@Valid @RequestBody OrderRequestDto request) {
        log.info("Post/order -> Creating the order with: {} and {}", request.getClientDocument(), request.getProductCode());
        return Mono.just(request)
                .map(orderDtoMapper::toCreateCommand)
                .flatMap(createOrderUseCase::createOrder)
                .map(orderDtoMapper::toResponseDto)
                .doOnSuccess(response ->
                        log.info("Order created by code: {}", response.getUuid()));

    }

    @DeleteMapping("/{uuid}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> deleteOrder(@PathVariable String uuid){
        log.info("Delete/clients/{} -Deleting Client", uuid);
        return deleteOrderUseCase.deleteOrder(uuid)
                .doOnSuccess(  V->
                        log.info("order deleted sucessfully: {}",uuid));

    }

    @GetMapping(
            value = "/{uuid}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public Mono<OrderResponseDto> getOrder(@PathVariable String uuid) {

        log.info("GET /orders/{} - Retrieving order", uuid);

        return getOrderUseCase.getOrderByUuid(uuid)
                .map(orderDtoMapper::toResponseDto)
                .doOnSuccess(response ->
                        log.info("Client retrieved successfully: {}", response.getUuid())
                );
    }
}
