package com.co.grandmasfood.infrastructure.adapter.out.persistence.repository;

import com.co.grandmasfood.infrastructure.adapter.out.persistence.entity.OrderEntity;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface OrderR2dbcRepository extends R2dbcRepository<OrderEntity, Long> {
    Mono<OrderEntity>  findByUuid(String uuid);

    Mono<Void> deleteByUuid(String uuid);
}
