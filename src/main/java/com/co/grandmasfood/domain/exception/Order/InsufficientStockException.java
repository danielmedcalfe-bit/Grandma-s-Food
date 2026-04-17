package com.co.grandmasfood.domain.exception.Order;

import com.co.grandmasfood.domain.exception.Client.DomainException;

public class InsufficientStockException extends DomainException {

    public InsufficientStockException(String productCode, Integer available, Integer requested) {
        super("INSUFFICIENT_STOCK",
                "Product " + productCode + " only has " + available +
                        " units available, but " + requested + " were requested");
    }
}