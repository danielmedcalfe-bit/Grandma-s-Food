package com.co.grandmasfood.infrastructure.adapter.in.rest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClientRequestDto {

    @NotBlank(message = "Document is required")
    @Size(max = 20, message = "Document must not exceed 20 characters")
    @Pattern(
            regexp = "^(CC|CE|P|TI|NIT|PPT)-[0-9]{1,15}$",
            message = "Invalid document format. Expected: TYPE-NUMBER (e.g., CC-123456)"
    )
    @JsonProperty("document")
    private String document;

    @NotBlank(message = "Name is required")
    @Size(max = 255, message = "Name must not exceed 255 characters")
    @JsonProperty("name")
    private String name;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    @Size(max = 255, message = "Email must not exceed 255 characters")
    @JsonProperty("email")
    private String email;

    @NotBlank(message = "Phone is required")
    @Pattern(
            regexp = "^[0-9]{10}$",
            message = "Phone must be exactly 10 digits"
    )
    @JsonProperty("phone")
    private String phone;

    @NotBlank(message = "Address is required")
    @Size(max = 500, message = "Address must not exceed 500 characters")
    @JsonProperty("address")
    private String address;
}