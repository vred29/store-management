package com.example.store.store_management.product.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "product")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(length = 1000)
    private String description; // optional

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @Column(nullable = false)
    private Integer quantityInStock;


    @Column(nullable = false)
    private boolean deleted = false;

    public void changePrice(BigDecimal newPrice) {
        this.price = newPrice;
    }

    public void updateStock(Integer newQuantity) {
        this.quantityInStock = newQuantity;
    }
    public void updateDetails(String newName, String newDescription) {
        if (newName != null) {
            this.name = newName;
        }
        if (newDescription != null) {
            this.description = newDescription;
        }
    }
    public void softDelete() {
        this.deleted = true;
    }

    public Product(String name, String description, BigDecimal price, Integer quantityInStock) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.quantityInStock = quantityInStock;
    }
}
