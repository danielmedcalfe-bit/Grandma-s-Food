package com.co.grandmasfood.infrastructure.adapter.in.rest.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequestDto{
    @NotBlank(message="the client document is required")
    @Size(max=20, message="the client document must not exceed the 20 characters")
    private String clientDocument;

    @NotBlank(message="the product code is required")
    @Size(max=50,message="the product code must not exceed the 50 characters")
    @Pattern(
            regexp = "^[A-Z0-9-]+$",
            message = "Product code must contain only uppercase letters, numbers, and hyphens"
    )
    private String productCode;


    @NotNull(message="the cuantity is required")
    @Min(value=1, message= "the cuantity must contain at least 1 value")
    @Max(value=99, message= "the cuantity must contain a value less than 100")
    private Integer quantity;



    @Size(max=511,message="the additional Information must not exceed the 511 characters")
    private String additionalInfo;

}
