package com.co.grandmasfood.application.port.in.product;

import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;

@Value
@Builder
public class CreateProductCommand {
    String code;
    String name;
    String description;
    BigDecimal price;
    Integer stock;
    String category;
}
