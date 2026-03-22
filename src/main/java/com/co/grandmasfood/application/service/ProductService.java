package com.co.grandmasfood.application.service;

import com.co.grandmasfood.application.port.in.product.*;
import com.co.grandmasfood.application.port.out.ProductPersistencePort;
import com.co.grandmasfood.application.port.out.ProductPersistencePort;
import com.co.grandmasfood.domain.exception.Client.NoChangesDetectedException;
import com.co.grandmasfood.domain.exception.Product.NotFoundUpdateException;
import com.co.grandmasfood.domain.exception.Product.ProductAlreadyExistsException;
import com.co.grandmasfood.domain.exception.Product.ProductNotFoundException;
import com.co.grandmasfood.domain.model.Product;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService implements CreateProductUseCase, GetProductUseCase, UpdateProductUseCase, DeleteProductUseCase {

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
    @Override
    public Mono<Product>  getProductByCode(String code){
         log.info("Retrieving Product by Code: {}", code);

                 return productPersistencePort.findByCode(code)
                         .switchIfEmpty(Mono.error(new ProductNotFoundException(code)))
                         .doOnSuccess(product-> {
                             log.info("Product Founded with code: {}",product.getCode());
                         });




    }
    @Override
    public Mono<Void> updateByCode(String code, UpdateProductCommand command){
        log.info("Updating Product by code: {}", code);
        if(!command.hasChanged()){
            log.warn("No Changes detected by code: {}", code);
            return Mono.error(new NotFoundUpdateException("No changes detected by the request"));
        }
        return productPersistencePort.findByCode(code)
                .switchIfEmpty(Mono.error(new ProductNotFoundException(code)))
                .flatMap(existingProduct -> {
                    Product updatedProduct=applyUpdate(existingProduct, command);
                    if (!hasActualChanges(updatedProduct, existingProduct)) {
                        log.warn("i've not detected changes ");
                                return Mono.error(new NoChangesDetectedException("No changes detectec in the request"));
                    }
                    updatedProduct.setUpdatedAt(LocalDateTime.now());
                    return productPersistencePort.save(updatedProduct);
                })
                .then()
                .doOnSuccess(v ->
                        log.info("Product updated correctly with code: {}", code));
    }
    @Override
    public Mono<Void> deleteProduct(String code){
        log.info("Deleting Products with code: {}", code);
        return productPersistencePort.findByCode(code)
                .switchIfEmpty(Mono.error(new ProductNotFoundException(code)))
                .flatMap( Product -> productPersistencePort.deleteByCode(code))
                .doOnSuccess(Void -> log.info("Product deleted correctly by code: {}",code));
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

    private Product applyUpdate(Product existing, UpdateProductCommand command){
        return Product.builder()
                .code(existing.getCode())
                .name(command.getName() != null ? command.getName(): existing.getName())
                .description(command.getDescription() != null ? command.getDescription():existing.getDescription())
                .price(command.getPrice() != null ? command.getPrice():existing.getPrice())
                .stock(command.getStock() !=null ? command.getStock():existing.getStock())
                .category(command.getCategory() !=null ? command.getCategory():existing.getCategory())
                .createdAt(existing.getCreatedAt())
                .updatedAt(existing.getUpdatedAt())
                .build();

    }
    private boolean hasActualChanges(Product existing, Product updated){
        return !existing.getName().equals(updated.getName()) ||
                !existing.getDescription().equals(updated.getDescription()) ||
                 !existing.getPrice().equals(updated.getPrice())||
                !existing.getCategory().equals(updated.getCategory())||
                !existing.getStock().equals(updated.getStock());



    }

}
