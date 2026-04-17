package com.co.grandmasfood.application.port.in.order;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class OrderCreateCommand {
    String clientDocument;
    String productCode;
    Integer quantity;
    String additionalInfo;
}
