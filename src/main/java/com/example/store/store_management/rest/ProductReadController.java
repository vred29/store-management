package com.example.store.store_management.rest;

import com.example.store.store_management.domain.ProductService;
import com.example.store.store_management.rest.dto.ProductDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductReadController {
    final ProductService productService;

    @GetMapping
    public Page<ProductDto> getAllProducts(Pageable pageable) {
        return productService.getProducts(pageable);
    }

    @GetMapping("/search")
    public Page<ProductDto> searchByName(
            @RequestParam String name,
            Pageable pageable) {
        return productService.searchByName(name, pageable);
    }

    @GetMapping("/{id}")
    public ProductDto getById(@PathVariable Long id) {
        return productService.getById(id);
    }
}
