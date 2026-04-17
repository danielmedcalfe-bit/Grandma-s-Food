package com.co.grandmasfood.infrastructure.adapter.in.rest.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateProductResquestDto {
    @NotBlank(message="if is send the name is required")
    @Size(max=255, message="The name must not exceed the 255 characters")
    private String name;

    @Size(max=500,message="The description must not exceed the 500 characters")
    private String description;

    @DecimalMin(value="0.01", message = "the Price must be grater than zero")
    @Digits(integer = 10, fraction=2,message="The price must not exceed the 10 digits and the 2 decimals")
    private BigDecimal price;

    @DecimalMin(value="0.01")
    private Integer stock;

    @NotBlank(message="if is send the category is required")
    @Size(max=100, message="The description must not  exceed the 100 characters")
    private String category;
}
