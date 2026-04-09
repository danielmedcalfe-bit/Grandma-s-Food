package com.co.grandmasfood.infrastructure.adapter.out.persistence.mapper;

import com.co.grandmasfood.domain.model.Order;
import com.co.grandmasfood.infrastructure.adapter.out.persistence.entity.OrderEntity;
import org.springframework.stereotype.Component;

@Component
public class OrderEntityMapper {

    public OrderEntity toEntity(Order domain){
        return OrderEntity.builder()
                .uuid(domain.getUuid())
                .clientDocument(domain.getClientDocument())
                .productCode(domain.getProductCode())
                .quantity(domain.getQuantity())
                .additionalInfo(domain.getAdditionalInfo())
                .unitPriceWithoutTax(domain.getUnitPriceWithoutTax())
                .subtotalWithoutTax(domain.getSubtotalWithoutTax())
                .taxAmount(domain.getTaxAmount())
                .totalWithTax(domain.getTotalWithTax())
                .delivered(domain.getDelivered())
                .orderDate(domain.getOrderDate())
                .deliveredDate(domain.getDeliveredDate())
                .createdAt(domain.getCreatedAt())
                .updatedAt(domain.getUpdatedAt())
                .build();
    }

    public Order toDomain(OrderEntity entity){
        return Order.builder()
                .uuid(entity.getUuid())
                .clientDocument(entity.getClientDocument())
                .productCode(entity.getProductCode())
                .quantity(entity.getQuantity())
                .additionalInfo(entity.getAdditionalInfo())
                .unitPriceWithoutTax(entity.getUnitPriceWithoutTax())
                .subtotalWithoutTax(entity.getSubtotalWithoutTax())
                .taxAmount(entity.getTaxAmount())
                .totalWithTax(entity.getTotalWithTax())
                .delivered(entity.getDelivered())
                .orderDate(entity.getOrderDate())
                .deliveredDate(entity.getDeliveredDate())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }
}
