package com.co.grandmasfood.application.port.in.product;

import lombok.Builder;
import lombok.Value;

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
      return name != null || description != null || price != null || stock != null || category != null ;
  }
}
