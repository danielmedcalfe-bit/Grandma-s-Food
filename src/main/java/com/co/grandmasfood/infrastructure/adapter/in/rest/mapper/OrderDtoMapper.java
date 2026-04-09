package com.co.grandmasfood.infrastructure.adapter.in.rest.mapper;

import com.co.grandmasfood.application.port.in.order.OrderCreateCommand;
import com.co.grandmasfood.domain.model.Order;
import com.co.grandmasfood.infrastructure.adapter.in.rest.dto.OrderRequestDto;
import com.co.grandmasfood.infrastructure.adapter.in.rest.dto.OrderResponseDto;
import org.springframework.stereotype.Component;

@Component
public class OrderDtoMapper {

    public OrderCreateCommand toCreateCommand(OrderRequestDto orderRequestDto) {
        return OrderCreateCommand.builder()
                .clientDocument(orderRequestDto.getClientDocument())
                .productCode(orderRequestDto.getProductCode())
                .quantity(orderRequestDto.getQuantity())
                .additionalInfo(orderRequestDto.getAdditionalInfo())
                .build();
    }
    public OrderResponseDto toResponseDto(Order order){
        return OrderResponseDto.builder()
                .uuid(order.getUuid())
                .clientDocument(order.getClientDocument())
                .productCode(order.getProductCode())
                .quantity(order.getQuantity())
                .additionalInfo(order.getAdditionalInfo())
                .unitPriceWithoutTax(order.getUnitPriceWithoutTax())
                .subtotalWithoutTax(order.getSubtotalWithoutTax())
                .taxAmount(order.getTaxAmount())
                .totalWithTax(order.getTotalWithTax())
                .delivered(order.getDelivered())
                .orderDate(order.getOrderDate())
                .deliveredDate(order.getDeliveredDate())
                .build();

    }
}
