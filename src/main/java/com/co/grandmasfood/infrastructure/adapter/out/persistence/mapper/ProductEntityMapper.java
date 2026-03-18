package com.co.grandmasfood.infrastructure.adapter.out.persistence.mapper;


import com.co.grandmasfood.domain.model.Product;
import com.co.grandmasfood.infrastructure.adapter.out.persistence.entity.ProductEntity;
import org.springframework.stereotype.Component;

@Component
public class ProductEntityMapper {

    public ProductEntity toEntity(Product domain){
        return ProductEntity.builder()
                .name(domain.getName())
                .description(domain.getDescription())
                .price(domain.getPrice())
                .stock(domain.getStock())
                .code(domain.getCode())
                .category(domain.getCategory())
                .createdAt(domain.getCreatedAt())
                .updatedAt(domain.getUpdatedAt())
                .build();


    }

    public Product toDomain(ProductEntity entity){
        return Product.builder()
                .name(entity.getName())
                .code(entity.getCode())
                .description(entity.getDescription())
                .price(entity.getPrice())
                .stock(entity.getStock())
                .category(entity.getCategory())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();

    }

}
