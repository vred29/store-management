package com.example.store.store_management;

import com.example.store.store_management.product.domain.Product;
import com.example.store.store_management.product.domain.ProductRepository;
import com.example.store.store_management.product.rest.ProductCommandController;
import com.example.store.store_management.product.rest.dto.AddProductDto;
import com.example.store.store_management.product.rest.dto.UpdateProductDetailsDto;
import com.example.store.store_management.product.service.ProductService;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.security.test.context.support.WithMockUser;

import java.math.BigDecimal;
import java.nio.file.AccessDeniedException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
class StoreManagementApplicationTests {

	@Autowired
	private ProductService productService;

	@Autowired
	private ProductCommandController productCommandController;

	@Autowired
	private ProductRepository productRepository;

	@Nested
	public class Add {
		@Test
		@WithMockUser(username = "admin", roles = {"ADMIN"})
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
		@WithMockUser(username = "admin", roles = {"VIEWER", "EMPLOYEE"})
		void addProduct_forbidden() {
			AddProductDto dto = new AddProductDto("Laptop", "Gaming laptop",
					new BigDecimal("2000.00"), 5);

			assertThatThrownBy(() -> productCommandController.addProduct(dto))
					.isInstanceOf(AuthorizationDeniedException.class);
		}
	}

	@Nested
	public class Update {
		@Test
		void updatePrice_shouldPersistNewPrice() {
			Product product = productRepository.save(new Product("Mouse", "Wireless mouse", new BigDecimal("20.0"), 10));
			productService.updatePrice(product.getId(), new BigDecimal("25.00"));

			Product updated = productRepository.findById(product.getId()).orElseThrow();
			assertThat(updated.getPrice()).isEqualTo(new BigDecimal("25.00"));
		}

		@Test
		@WithMockUser(username = "admin", roles = {"VIEWER"})
		void updatePrice_forbid() {
			Product product = productRepository.save(new Product("Mouse", "Wireless mouse", new BigDecimal("20.0"), 10));
			productService.updatePrice(product.getId(), new BigDecimal("25.0"));

			assertThatThrownBy(() ->
			productCommandController.changePrice(product.getId(),  new BigDecimal("25.0")))
					.isInstanceOf(AuthorizationDeniedException.class);
		}

		@Test
		void updateStock_shouldPersistNewQuantity() {
			Product product = productRepository.save(new Product("Keyboard", "Mechanical", new BigDecimal("50.0"), 15));
			productService.updateStock(product.getId(), 30);

			Product updated = productRepository.findById(product.getId()).orElseThrow();
			assertThat(updated.getQuantityInStock()).isEqualTo(30);
		}

		@Test
		@WithMockUser(username = "admin", roles = {"VIEWER"})
		void updateStock_forbid() {
			Product product = productRepository.save(new Product("Keyboard", "Mechanical", new BigDecimal("50.0"), 15));
			productService.updateStock(product.getId(), 30);

			assertThatThrownBy(() ->
					productCommandController.updateStock(product.getId(), 30))
					.isInstanceOf(AuthorizationDeniedException.class);
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
		@WithMockUser(username = "admin", roles = {"VIEWER"})
		void updateDetails_forbid() {
			Product product = productRepository.save(new Product("Monitor", "HD monitor", new BigDecimal("150.0"), 20));
			UpdateProductDetailsDto dto = new UpdateProductDetailsDto("Ultra Monitor", "4K Ultra Monitor");

			assertThatThrownBy(() ->
					productCommandController.updateProductDetails(product.getId(), dto))
					.isInstanceOf(AuthorizationDeniedException.class);
		}


	}

	@Nested
	public class Delete {
		@Test
		void softDelete_shouldMarkDeletedFlagTrue() {
			Product product = productRepository.save(new Product("Chair", "Office chair", new BigDecimal("120.0"), 5));
			productService.deleteProduct(product.getId());

			Product deleted = productRepository.findById(product.getId()).orElseThrow();
			assertThat(deleted.isDeleted()).isTrue();
		}

		@Test
		@WithMockUser(username = "admin", roles = {"VIEWER", "EMPLOYEE"})
		void softDelete_forbidden() {
			Product product = productRepository.save(new Product("Chair", "Office chair", new BigDecimal("120.0"), 5));

			assertThatThrownBy(() -> productCommandController.deleteProduct(product.getId()))
					.isInstanceOf(AuthorizationDeniedException.class);
		}
	}

}
