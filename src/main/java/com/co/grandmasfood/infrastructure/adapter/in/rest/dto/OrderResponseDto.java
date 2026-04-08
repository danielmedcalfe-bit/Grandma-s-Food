package com.co.grandmasfood.infrastructure.adapter.in.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponseDto {
    private String uuid;
    private String clientDocument;
    private String productCode;
    private Integer quantity;
    private String additionalInfo;

    // Campos calculados
    private BigDecimal unitPriceWithoutTax;
    private BigDecimal subtotalWithoutTax;
    private BigDecimal taxAmount;
    private BigDecimal totalWithTax;

    // Estado
    private Boolean delivered;
    private LocalDateTime orderDate;
    private LocalDateTime deliveredDate;
}
