package com.example.store.store_management.product.rest.dto;

import jakarta.validation.constraints.Size;

public record UpdateProductDetailsDto(
        @Size(min = 1, message = "Name must not be empty")
        String name,

        String description
) {
}
