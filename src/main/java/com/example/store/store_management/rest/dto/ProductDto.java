package com.example.store.store_management.rest.dto;

import java.math.BigDecimal;

public record ProductDto(
        Long id,
        String name,
        BigDecimal price,
        Integer quantityInStock
) {
}
