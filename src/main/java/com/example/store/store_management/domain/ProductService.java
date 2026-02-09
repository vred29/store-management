package com.example.store.store_management.domain;

import com.example.store.store_management.rest.dto.AddProductDto;
import com.example.store.store_management.rest.dto.ProductDto;
import com.example.store.store_management.rest.dto.UpdateProductDetailsDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class ProductService {
    final ProductRepository productRepository;

    public Product addProduct(Product product) {
        return productRepository.save(product);
    }

    public Page<ProductDto> getProducts(Pageable pageable) {
        Page<Product> products = productRepository.findAllByDeletedFalse(pageable);
        return products.map(product -> new ProductDto(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getQuantityInStock()
        ));
    }

    public ProductDto getById(Long id) {
        Product product = productRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        return new ProductDto(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getQuantityInStock()
        );
    }

    public Page<ProductDto> searchByName(String name, Pageable pageable) {
        return productRepository
                .findByNameContainingIgnoreCaseAndDeletedFalse(name, pageable)
                .map(product -> new ProductDto(
                        product.getId(),
                        product.getName(),
                        product.getDescription(),
                        product.getPrice(),
                        product.getQuantityInStock()
                ));
    }

    public Long addProduct(AddProductDto dto) {
        Product product = new Product(
                dto.name(),
                dto.description(),
                dto.price(),
                dto.quantityInStock()
        );

        Product saved = productRepository.save(product);
        return saved.getId();
    }

    public void updateProductDetails(Long id, UpdateProductDetailsDto dto) {
        Product product = productRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        product.updateDetails(dto.name(), dto.description());
        productRepository.save(product);
    }

    public Product updatePrice(Long productId, BigDecimal newPrice) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        product.changePrice(newPrice);
        return productRepository.save(product);
    }

    public Product updateStock(Long productId, Integer newQuantity) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        product.updateStock(newQuantity);
        return productRepository.save(product);
    }

    public void deleteProduct(Long id) {
        Product product = productRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        product.softDelete();
        productRepository.save(product);
    }
}
