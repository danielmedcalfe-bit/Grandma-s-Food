package com.co.grandmasfood.infrastructure.adapter.out.persistence.repository;

import com.co.grandmasfood.infrastructure.adapter.out.persistence.entity.ProductEntity;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface ProductR2dbcRepository extends R2dbcRepository<ProductEntity, Long> {
    Mono<ProductEntity>  findByCode(String code);
    Mono<Boolean> existsByCode(String code);
    Mono<Void> deleteByCode(String code);

}
