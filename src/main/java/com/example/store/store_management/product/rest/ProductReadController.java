package com.example.store.store_management.product.rest;

import com.example.store.store_management.product.service.ProductService;
import com.example.store.store_management.product.rest.dto.ProductDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductReadController {
    final ProductService productService;

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','EMPLOYEE','VIEWER')")
    public Page<ProductDto> getAllProducts(Pageable pageable) {
        return productService.getProducts(pageable);
    }

    @GetMapping("/search")
    @PreAuthorize("hasAnyRole('ADMIN','EMPLOYEE','VIEWER')")
    public Page<ProductDto> searchByName(
            @RequestParam String name,
            Pageable pageable) {
        return productService.searchByName(name, pageable);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','EMPLOYEE','VIEWER')")
    public ProductDto getById(@PathVariable Long id) {
        return productService.getById(id);
    }
}
