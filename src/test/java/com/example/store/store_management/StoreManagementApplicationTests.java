package com.example.store.store_management;

import com.example.store.store_management.product.domain.Product;
import com.example.store.store_management.product.domain.ProductRepository;
import com.example.store.store_management.product.rest.dto.AddProductDto;
import com.example.store.store_management.product.rest.dto.UpdateProductDetailsDto;
import com.example.store.store_management.product.service.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class StoreManagementApplicationTests {

	@Autowired
	private ProductService productService;

	@Autowired
	private ProductRepository productRepository;

	@Test
	void addProduct_shouldSaveProductInH2() {
		AddProductDto dto = new AddProductDto("Laptop", "Gaming laptop",
				new BigDecimal("2000.00"), 5);

		Long id = productService.addProduct(dto);

		Optional<Product> saved = productRepository.findById(id);
		assertThat(saved.isPresent()).isTrue();
		assertThat(saved.get().getName()).isEqualTo("Laptop");
		assertThat(saved.get().getQuantityInStock()).isEqualTo(5);
	}

	@Test
	void updatePrice_shouldPersistNewPrice() {
		Product product = productRepository.save(new Product("Mouse", "Wireless mouse", new BigDecimal("20.0"), 10));
		productService.updatePrice(product.getId(), new BigDecimal("25.0"));

		Product updated = productRepository.findById(product.getId()).orElseThrow();
		assertThat(updated.getPrice()).isEqualTo(new BigDecimal("25.00"));
	}

	@Test
	void updateStock_shouldPersistNewQuantity() {
		Product product = productRepository.save(new Product("Keyboard", "Mechanical", new BigDecimal("50.0"), 15));
		productService.updateStock(product.getId(), 30);

		Product updated = productRepository.findById(product.getId()).orElseThrow();
		assertThat(updated.getQuantityInStock()).isEqualTo(30);
	}

	@Test
	void updateDetails_shouldUpdateNameAndDescription() {
		Product product = productRepository.save(new Product("Monitor", "HD monitor", new BigDecimal("150.0"), 20));
		UpdateProductDetailsDto dto = new UpdateProductDetailsDto("Ultra Monitor", "4K Ultra Monitor");

		productService.updateProductDetails(product.getId(), dto);

		Product updated = productRepository.findById(product.getId()).orElseThrow();
		assertThat(updated.getName()).isEqualTo("Ultra Monitor");
		assertThat(updated.getDescription()).isEqualTo("4K Ultra Monitor");
	}

	@Test
	void softDelete_shouldMarkDeletedFlagTrue() {
		Product product = productRepository.save(new Product("Chair", "Office chair", new BigDecimal("120.0"), 5));
		productService.deleteProduct(product.getId());

		Product deleted = productRepository.findById(product.getId()).orElseThrow();
		assertThat(deleted.isDeleted()).isTrue();
	}

}
