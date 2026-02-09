package com.example.store.store_management.product.rest;

import com.example.store.store_management.product.service.ProductService;
import com.example.store.store_management.product.rest.dto.AddProductDto;
import com.example.store.store_management.product.rest.dto.UpdateProductDetailsDto;
import com.example.store.store_management.util.GenericResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductCommandController {
    final ProductService productService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<GenericResponse> addProduct(@Valid @RequestBody AddProductDto dto) {

        Long productId = productService.addProduct(dto);

        return GenericResponse.success("Product added successfully", productId);
    }


    @PutMapping("/{id}/price")
    @PreAuthorize("hasAnyRole('ADMIN','EMPLOYEE')")
    public ResponseEntity<GenericResponse> changePrice(
            @PathVariable Long id,
            @RequestParam @Positive BigDecimal newPrice) {

        productService.updatePrice(id, newPrice);
        return GenericResponse.success("Price updated", id);
    }

    @PutMapping("/{id}/stock")
    @PreAuthorize("hasAnyRole('ADMIN','EMPLOYEE')")
    public ResponseEntity<GenericResponse> updateStock(
            @PathVariable Long id,
            @RequestParam @Positive Integer newQuantity) {

        productService.updateStock(id, newQuantity);
        return GenericResponse.success("Stock updated", id);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<GenericResponse> updateProductDetails(
            @PathVariable Long id,
            @Valid @RequestBody UpdateProductDetailsDto dto) {

        productService.updateProductDetails(id, dto);
        return GenericResponse.success("Product updated", id);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<GenericResponse> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return GenericResponse.success("Product deleted", id);
    }

}
