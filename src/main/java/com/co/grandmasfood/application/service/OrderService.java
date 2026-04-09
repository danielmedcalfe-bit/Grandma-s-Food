package com.co.grandmasfood.application.service;

import com.co.grandmasfood.application.port.in.order.*;
import com.co.grandmasfood.application.port.in.product.UpdateProductCommand;
import com.co.grandmasfood.application.port.out.ClientPersistencePort;
import com.co.grandmasfood.application.port.out.OrderPersistencePort;
import com.co.grandmasfood.application.port.out.ProductPersistencePort;
import com.co.grandmasfood.domain.exception.Client.ClientNotFoundException;
import com.co.grandmasfood.domain.exception.Client.NoChangesDetectedException;
import com.co.grandmasfood.domain.exception.Order.InsufficientStockException;
import com.co.grandmasfood.domain.exception.Order.OrderNotFoundException;
import com.co.grandmasfood.domain.exception.Product.NotFoundUpdateException;
import com.co.grandmasfood.domain.exception.Product.ProductNotFoundException;
import com.co.grandmasfood.domain.model.Client;
import com.co.grandmasfood.domain.model.Order;
import com.co.grandmasfood.domain.model.Product;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;


@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService implements CreateOrderUseCase, DeleteOrderUseCase, GetOrderUseCase, UpdateOrderUseCase {

    public final OrderPersistencePort orderPersistencePort;
    public final ClientPersistencePort clientPersistencePort;
    public final ProductPersistencePort productPersistencePort;

    private static final BigDecimal TAX_RATE= new BigDecimal("0.19");




    @Override
    public Mono<Order> createOrder(OrderCreateCommand command) {
          log.info("Create Order by Client: {} and product: {} ", command.getClientDocument(), command.getProductCode());

         return clientPersistencePort.findByDocument(command.getClientDocument())
             .switchIfEmpty(Mono.error(new ClientNotFoundException(command.getClientDocument())))
                 .doOnSuccess( Client -> log.info("we've found the client succesfully by Document:", command.getClientDocument()))

             .flatMap(client -> productPersistencePort.findByCode(command.getProductCode())
                        .switchIfEmpty(Mono.error(new ProductNotFoundException(command.getProductCode()))))
                         .flatMap(product -> {
                            if(product.getStock() < command.getQuantity()){
                                return Mono.error(new InsufficientStockException(product.getCode(),product.getStock(),command.getQuantity()));
                            }


                Order order=buildOrder(command, product);
                order.validate();


                 return reduceStock(product, command.getQuantity())
                .then(Mono.just(order));
                        })

            .flatMap(orderPersistencePort::save)
           .doOnSuccess(savedOrder ->
               log.info("Order created successfully with UUID: {}", savedOrder.getUuid()))
            .doOnError(error ->
                log.error("Error creating order: {}", error.getMessage()));
    }

    private Order buildOrder(OrderCreateCommand command, Product product){
        LocalDateTime now=LocalDateTime.now();
        BigDecimal unitPrice=product.getPrice();
        BigDecimal subtotal=unitPrice.multiply(new BigDecimal(command.getQuantity()));
        BigDecimal  tax=subtotal.multiply(TAX_RATE);
        BigDecimal total=subtotal.add(tax);

        return Order.builder()
                .uuid(UUID.randomUUID().toString())
                .clientDocument(command.getClientDocument())
                .productCode(command.getProductCode())
                .quantity(command.getQuantity())
                .additionalInfo(command.getAdditionalInfo())
                .unitPriceWithoutTax(unitPrice)
                .subtotalWithoutTax(subtotal)
                .taxAmount(tax)
                .totalWithTax(total)
                .delivered(false)
                .orderDate(now)
                .deliveredDate(null)
                .createdAt(now)
                .updatedAt(now)
                .build();
    }

    private Mono<Void> reduceStock(Product product, Integer quantity){
        product.setStock(product.getStock() - quantity);
        product.setUpdatedAt(LocalDateTime.now());

        return productPersistencePort.save(product)
                .then()
                .doOnSuccess(v ->
                        log.info("Stuck reduce for product {}: new Stock ={}",product.getCode(), product.getStock()));

    }

    @Override
    public Mono<Void> deleteOrder(String uuid) {
        log.info("Deleting client by document: {}", uuid);
        return orderPersistencePort.findByUuid(uuid)
                .switchIfEmpty(Mono.error(new OrderNotFoundException(uuid)))
                .flatMap(Client -> orderPersistencePort.deleteByUuid(uuid))
                .doOnSuccess(v -> log.info("Client deleted Successfully: {}", uuid));

    }

    @Override
    public Mono<Order> getOrderByUuid(String uuid) {

        log.info("Retrieving client with document: {}", uuid);

        return orderPersistencePort.findByUuid(uuid)
                .switchIfEmpty(Mono.error(new ClientNotFoundException(uuid)))
                .doOnSuccess(Order ->
                        log.info("Client found: {}", Order.getUuid())
                );
    }

    @Override
    public Mono<Void> updateByUuid(String uuid, UpdateOrderCommand command){
        log.info("Updating Order by Uuid: {}", uuid);
        if(!command.hasChanged()){
            log.warn("No Changes detected by code: {}", uuid);
            return Mono.error(new NotFoundUpdateException("No changes detected by the request"));
        }
        return orderPersistencePort.findByUuid(uuid)
                .switchIfEmpty(Mono.error(new OrderNotFoundException(uuid)))

                .flatMap(existingOrder -> {
                    Order updatedOrder=applyUpdate(existingOrder, command);
                    if (!hasActualChanges(updatedOrder, existingOrder)) {
                        log.warn("i've not detected changes ");
                        return Mono.error(new NoChangesDetectedException("No changes detectec in the request"));
                    }
                    updatedOrder.setUpdatedAt(LocalDateTime.now());
                    return orderPersistencePort.save(updatedOrder);
                })
                .then()
                .doOnSuccess(v ->
                        log.info("Order updated correctly with Uuid: {}", uuid));
    }








    private Order applyUpdate(Order existing, UpdateOrderCommand command){
      return Order.builder()
              .uuid(existing.getUuid())
              .clientDocument(existing.getClientDocument())
              .productCode(command.getProductCode() != null ? command.getProductCode():existing.getProductCode())
              .quantity(command.getQuantity() != null ? command.getQuantity():existing.getQuantity())
              .additionalInfo(command.getAdditionalInfo() != null ? command.getAdditionalInfo():existing.getAdditionalInfo())
              .unitPriceWithoutTax(existing.getUnitPriceWithoutTax())
              .updatedAt(existing.getUpdatedAt())
              .orderDate(existing.getOrderDate())
              .createdAt(existing.getCreatedAt())
              .delivered(existing.getDelivered())
              .deliveredDate(existing.getDeliveredDate())
              .subtotalWithoutTax(existing.getSubtotalWithoutTax())
              .taxAmount(existing.getTaxAmount())
              .totalWithTax(existing.getTotalWithTax())
              .build();
    }

    private boolean hasActualChanges(Order existing, Order updated){
        return !existing.getProductCode().equals(updated.getProductCode()) ||
                !existing.getQuantity().equals(updated.getQuantity()) ||
                !existing.getAdditionalInfo().equals(updated.getAdditionalInfo());


    }



}

