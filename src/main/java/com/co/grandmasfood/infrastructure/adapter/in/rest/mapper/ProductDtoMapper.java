package com.co.grandmasfood.infrastructure.adapter.in.rest.mapper;

import com.co.grandmasfood.application.port.in.product.CreateProductCommand;
import com.co.grandmasfood.domain.model.Product;
import com.co.grandmasfood.infrastructure.adapter.in.rest.dto.ProductRequestDto;
import com.co.grandmasfood.infrastructure.adapter.in.rest.dto.ProductResponseDto;
import org.springframework.stereotype.Component;

@Component
public class ProductDtoMapper {
    public CreateProductCommand toCreateCommand(ProductRequestDto dto){
          return CreateProductCommand.builder()
                  .code(dto.getCode())
                  .name(dto.getName())
                  .description(dto.getDescription())
                  .price(dto.getPrice())
                  .stock(dto.getStock())
                  .category(dto.getCategory())
                  .build();
    }
    public ProductResponseDto toResponseDto(Product product){
        return ProductResponseDto.builder()
                .code(product.getCode())
                .name(product.getName())
                .category(product.getCategory())
                .Stock(product.getStock())
                .category(product.getCategory())
                .price(product.getPrice())
                .build();
    }
}
