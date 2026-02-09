package com.example.store.store_management.rest;

import com.example.store.store_management.domain.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductReadController {

    @GetMapping
    public Page<Product> getAllProducts(Pageable pageable) {
        return Page.empty();
    }
}
