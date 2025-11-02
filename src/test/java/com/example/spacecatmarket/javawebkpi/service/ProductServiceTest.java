package com.example.spacecatmarket.javawebkpi.service;

import com.example.spacecatmarket.javawebkpi.config.MapperTestConfiguration;
import com.example.spacecatmarket.javawebkpi.domain.Category;
import com.example.spacecatmarket.javawebkpi.domain.Product;
import com.example.spacecatmarket.javawebkpi.service.exception.ProductNotFoundException;
import com.example.spacecatmarket.javawebkpi.service.impl.ProductServiceImpl;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = ProductServiceImpl.class)
@Import(MapperTestConfiguration.class)
@DisplayName("Product Service Test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ProductServiceTest {

    private static final String PRODUCT_NAME = "Space Helmet";
    private static final double PRODUCT_PRICE = 250.5;
    private static final String PRODUCT_DESCRIPTION = "High-quality astronaut helmet";
    private static final Category CATEGORY = Category.builder()
            .id(3L)
            .name("Equipment")
            .build();

    @Autowired
    private ProductServiceImpl productService;

    @Test
    @DisplayName("Should return all products")
    @Order(1)
    void testFindProducts() {
        List<Product> products = productService.findProducts();

        assertNotNull(products);
        assertEquals(2, products.size());
        assertTrue(products.stream()
                .anyMatch(p -> p.getName().equals("Antigravity ball")));
        assertTrue(products.stream()
                .anyMatch(p -> p.getName().equals("Space milk")));
    }

    @Test
    @DisplayName("Should find product by ID")
    @Order(2)
    void testFindById() {
        Product product = productService.findById(1L);

        assertNotNull(product);
        assertEquals(1L, product.getId());
        assertEquals("Antigravity ball", product.getName());
        assertEquals(100.0, product.getPrice());
        assertNotNull(product.getCategory());
        assertEquals("Main", product.getCategory().getName());
    }

    @Test
    @DisplayName("Should add a new product")
    @Order(3)
    void testAddProduct() {
        Product newProduct = Product.builder()
                .name(PRODUCT_NAME)
                .description(PRODUCT_DESCRIPTION)
                .price(PRODUCT_PRICE)
                .category(CATEGORY)
                .build();

        Product added = productService.addProduct(newProduct);
        assertNotNull(added.getId());
        assertEquals(PRODUCT_NAME, added.getName());

        List<Product> all = productService.findProducts();
        assertEquals(3, all.size(), "There should be 3 products after adding one");
    }

    @Test
    @DisplayName("Should update an existing product")
    @Order(4)
    void testUpdateProduct() {
        Product updated = Product.builder()
                .name("Updated Helmet")
                .description("New version of helmet")
                .price(300.0)
                .category(CATEGORY)
                .build();

        Product result = productService.updateProduct(2L, updated);
        assertEquals(2L, result.getId());
        assertEquals("Updated Helmet", result.getName());

        Product fetched = productService.findById(2L);
        assertEquals("Updated Helmet", fetched.getName());
        assertEquals(300.0, fetched.getPrice());
    }

    @Test
    @DisplayName("Should delete product by ID")
    @Order(5)
    void testDeleteProduct() {
        productService.deleteProduct(2L);

        List<Product> remaining = productService.findProducts();
        assertEquals(2, remaining.size(), "There should be 2 products after deletion");

        assertThrows(ProductNotFoundException.class, () -> productService.findById(2L));
    }

    @Test
    @DisplayName("Should handle deleting non-existent product gracefully")
    @Order(6)
    void testDeleteNonExistentProduct() {
        assertDoesNotThrow(() -> productService.deleteProduct(999L));

        assertEquals(2, productService.findProducts().size());
    }
}
