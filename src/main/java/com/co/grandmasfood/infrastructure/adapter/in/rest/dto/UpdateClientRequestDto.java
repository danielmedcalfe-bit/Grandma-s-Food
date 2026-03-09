package com.co.grandmasfood.infrastructure.adapter.in.rest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateClientRequestDto {

    @Size(max = 255, message = "Name must not exceed 255 characters")


    @JsonProperty("name")
    private String name;

    @Email(message = "Invalid email format")

    @Size(max = 255, message = "Email must not exceed 255 characters")
    @JsonProperty("email")
    private String email;

    @Pattern(
            regexp = "^[0-9]{10}$",
            message = "Phone must be exactly 10 digits"
    )
    @JsonProperty("phone")
    private String phone;

    @Size(max = 500, message = "Address must not exceed 500 characters")
    @JsonProperty("address")
    private String address;


}