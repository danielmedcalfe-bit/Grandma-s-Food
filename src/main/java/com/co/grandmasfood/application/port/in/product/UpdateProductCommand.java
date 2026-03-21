package com.co.grandmasfood.application.port.in.product;

import lombok.Builder;
import lombok.Value;
import org.apache.commons.lang3.ObjectUtils;

import java.math.BigDecimal;

@Builder
@Value
public class UpdateProductCommand {
  String name;
  String description;
  BigDecimal price;
  Integer stock;
  String category;

  public boolean hasChanged(){
      return ObjectUtils.anyNull(name, description, price, stock, category);
  }
}
