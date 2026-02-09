package com.example.store.store_management.product.rest.dto;

import java.math.BigDecimal;

public record ProductDto(
        Long id,
        String name,
        String description,
        BigDecimal price,
        Integer quantityInStock
) {
}
