package com.example.store.store_management.rest;

import com.example.store.store_management.domain.Product;
import com.example.store.store_management.util.GenericResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/product")
public class ProductCommandController {


    @PostMapping
    public ResponseEntity<GenericResponse> addProduct(@RequestBody Product product) {
        return GenericResponse.success("Adaugat", "1");
    }


    @PutMapping("/{id}/price")
    public ResponseEntity<GenericResponse> changePrice(@PathVariable String id, @RequestParam BigDecimal newPrice) {
        return GenericResponse.success("Modificat pret", id);
    }

    @PutMapping("/{id}/stock")
    public ResponseEntity<GenericResponse> updateStock(@PathVariable String id, @RequestParam Integer newQuantity) {
        return GenericResponse.success("Modificat stoc", id);
    }

}
