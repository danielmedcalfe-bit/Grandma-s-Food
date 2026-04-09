package com.co.grandmasfood.infrastructure.adapter.in.rest.mapper;

import com.co.grandmasfood.application.port.in.product.CreateProductCommand;
import com.co.grandmasfood.application.port.in.product.UpdateProductCommand;
import com.co.grandmasfood.domain.model.Product;
import com.co.grandmasfood.infrastructure.adapter.in.rest.dto.ProductRequestDto;
import com.co.grandmasfood.infrastructure.adapter.in.rest.dto.ProductResponseDto;
import com.co.grandmasfood.infrastructure.adapter.in.rest.dto.UpdateProductResquestDto;
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
                .description(product.getDescription())
                .Stock(product.getStock())
                .category(product.getCategory())
                .price(product.getPrice())
                .build();
    }
    public UpdateProductCommand updateCommand(UpdateProductResquestDto productRequestDto){
        return UpdateProductCommand.builder()
                .name(productRequestDto.getName())
                .description(productRequestDto.getDescription())
                .price(productRequestDto.getPrice())
                .stock(productRequestDto.getStock())
                .category(productRequestDto.getCategory())
                .build();

    }
}
