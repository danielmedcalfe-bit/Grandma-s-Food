package com.co.grandmasfood.domain.exception.Order;

import com.co.grandmasfood.domain.exception.Client.DomainException;

public class OrderNotFoundException extends DomainException {

    public OrderNotFoundException(String uuid) {
        super("ORDER_NOT_FOUND",
                "Order with UUID " + uuid + " not found");
    }
}
