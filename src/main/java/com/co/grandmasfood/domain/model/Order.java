package com.co.grandmasfood.domain.model;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class Order {
    private String uuid;
    private String clientDocument;
    private String productCode;
    private Integer quantity;
    private String additionalInfo;

    private BigDecimal unitPriceWithoutTax;
    private BigDecimal subtotalWithoutTax;
    private BigDecimal taxAmount;
    private BigDecimal totalWithTax;


    private Boolean delivered;
    private LocalDateTime orderDate;
    private LocalDateTime deliveredDate;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public void validate(){
        if (clientDocument == null || clientDocument.isBlank()) {
            throw new IllegalArgumentException("Client document cannot be empty");
        }

        if (productCode == null || productCode.isBlank()) {
            throw new IllegalArgumentException("Product code cannot be empty");
        }

        if (quantity == null || quantity < 1 || quantity >= 100) {
            throw new IllegalArgumentException("Quantity must be between 1 and 99");
        }

        if (unitPriceWithoutTax == null || unitPriceWithoutTax.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Unit price must be greater than zero");
        }
    }
}

