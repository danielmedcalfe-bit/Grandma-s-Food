package com.co.grandmasfood.infrastructure.adapter.out.persistence.adapter;

import com.co.grandmasfood.application.port.out.OrderPersistencePort;
import com.co.grandmasfood.domain.model.Order;
import com.co.grandmasfood.infrastructure.adapter.out.persistence.mapper.OrderEntityMapper;
import com.co.grandmasfood.infrastructure.adapter.out.persistence.repository.OrderR2dbcRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
@Slf4j
public class OrderPersistenceAdapter implements OrderPersistencePort{

    private final OrderR2dbcRepository orderR2dbcRepository;
    private final OrderEntityMapper orderEntityMapper;



    @Override
    public Mono<Order> save(Order order){
        log.debug("Saving Orders with Uuid: {}", order.getUuid());

        return orderR2dbcRepository.findByUuid(order.getUuid())
                .flatMap(existingEntity -> {
                    log.debug("Order exists by id: {}, performing Update", existingEntity.getId());

                    var entityToUpdate= orderEntityMapper.toEntity(order);
                    entityToUpdate.setId(existingEntity.getId());

                    return orderR2dbcRepository.save(entityToUpdate);
                })


                .switchIfEmpty(
                        Mono.defer(() ->{
                                    log.debug("Order doesn't exists, performing INSERT");

                                    var entityToInsert=orderEntityMapper.toEntity(order);
                                    return orderR2dbcRepository.save(entityToInsert);
                                }
                        )
                )
                .map(orderEntityMapper::toDomain)
                .doOnSuccess(savedOrder ->
                        log.debug("Order saved correctly: {}", savedOrder.getUuid())
                );
    }
    @Override
    public Mono<Order> findByUuid(String uuid){
        log.debug("Finding Order by Uuid: {}", uuid);

        return orderR2dbcRepository.findByUuid(uuid)
              .map(orderEntityMapper::toDomain);
    }

    @Override
    public Mono<Void> deleteByUuid(String uuid) {
        log.debug("deleting order by Uuid: {}", uuid);

        return orderR2dbcRepository.deleteByUuid(uuid);
    }
}
