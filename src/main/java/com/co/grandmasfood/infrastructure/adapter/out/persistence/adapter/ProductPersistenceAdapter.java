package com.co.grandmasfood.infrastructure.adapter.out.persistence.adapter;

import com.co.grandmasfood.application.port.out.ProductPersistencePort;
import com.co.grandmasfood.domain.model.Product;
import com.co.grandmasfood.infrastructure.adapter.out.persistence.mapper.ProductEntityMapper;
import com.co.grandmasfood.infrastructure.adapter.out.persistence.repository.ProductR2dbcRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
@Slf4j
public class ProductPersistenceAdapter implements ProductPersistencePort {

    private  final ProductR2dbcRepository productR2dbcRepository;
    private  final ProductEntityMapper productEntityMapper;



    @Override
    public Mono<Product> save(Product product){
          log.debug("saving product with code: {}", product.getCode());

          return productR2dbcRepository.findByCode(product.getCode())
                  .flatMap(existingEntity -> {
                      log.debug("Product already exists with code: {}", existingEntity.getId());
                      var entityToUpdate=productEntityMapper.toEntity(product);
                      entityToUpdate.setId(existingEntity.getId());
                      return  productR2dbcRepository.save(entityToUpdate);
                  })
                  .switchIfEmpty(
                          Mono.defer(() -> {
                              log.debug("Inserting product ");
                              var entityToInsert=productEntityMapper.toEntity(product);
                              return productR2dbcRepository.save(entityToInsert);
                                  }

                          )
                  )
                  .map(productEntityMapper::toDomain)
                  .doOnSuccess(savedProduct -> {
                      log.debug("Produc saved wit code: {}", product.getCode());
                  });




    }

    @Override
    public Mono<Boolean> existsByCode(String code){
        log.debug("Verify if Product exist by code: {}", code);
                return productR2dbcRepository.existsByCode(code);
    }


    @Override
    public Mono<Product> findByCode(String code){
        log.debug("Finding product with code: {}", code);

        return productR2dbcRepository.findByCode(code)
               .map(productEntityMapper::toDomain);
    }

}
