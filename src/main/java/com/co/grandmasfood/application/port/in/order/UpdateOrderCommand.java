package com.co.grandmasfood.application.port.in.order;

import lombok.Builder;
import lombok.Value;
import org.apache.commons.lang3.ObjectUtils;

@Value
@Builder
public class UpdateOrderCommand {

    private String productCode;
    private Integer quantity;
    private String additionalInfo;


    public boolean hasChanged(){
        return ObjectUtils.anyNull(productCode, quantity, additionalInfo);
    }
}
