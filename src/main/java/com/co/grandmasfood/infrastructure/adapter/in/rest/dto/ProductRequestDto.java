package com.co.grandmasfood.infrastructure.adapter.in.rest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.relational.core.sql.Not;

import java.math.BigDecimal;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequestDto {

    @NotBlank(message = "code is required")
    @JsonProperty("code")
    private String code;

    @NotBlank(message="the name is required")
    @JsonProperty("name")
    private String name;

    @Size(max=500, message="description must no exceed 500 characters")
    @JsonProperty("descripion")
    private String description;

    @NotNull(message = "the price is required")
    @DecimalMin(value="0.01", message= "Product price must be grater that zero")
    @Digits(integer=10, fraction=2, message="product price must have 10 digits and 2 fraccion places")
    @JsonProperty("price")
    private BigDecimal  price;

    @NotNull(message = "The stock is required")
    @Min(value=0, message = "Stock cannot be negative")
    @JsonProperty("stock")
    private Integer stock;

    @NotBlank(message = "the category is required")
    @JsonProperty("category")
    private String category;
}
