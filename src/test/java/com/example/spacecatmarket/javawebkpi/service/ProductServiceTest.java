package com.example.spacecatmarket.javawebkpi.service;

import com.example.spacecatmarket.javawebkpi.domain.Category;
import com.example.spacecatmarket.javawebkpi.domain.Product;
import com.example.spacecatmarket.javawebkpi.integration.AbstractIntegrationTest;
import com.example.spacecatmarket.javawebkpi.service.exception.ProductNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@DisplayName("Product Service Integration Test")
public class ProductServiceTest extends AbstractIntegrationTest {

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    private static final String PRODUCT_NAME = "Space Helmet";
    private static final double PRODUCT_PRICE = 250.5;
    private static final String PRODUCT_DESCRIPTION = "High-quality astronaut helmet";

    @Test
    @DisplayName("Should add a new product")
    void testAddProduct() {
        Category category = categoryService.createCategory(Category.builder().name("Equipment").build());

        Product newProduct = Product.builder()
                .name(PRODUCT_NAME)
                .description(PRODUCT_DESCRIPTION)
                .price(PRODUCT_PRICE)
                .category(category)
                .build();

        Product added = productService.addProduct(newProduct);

        assertNotNull(added.getId(), "ID should be generated");
        assertEquals(PRODUCT_NAME, added.getName());
        assertEquals("Equipment", added.getCategory().getName());
    }

    @Test
    @DisplayName("Should find product by ID")
    void testFindById() {
        Category category = categoryService.createCategory(Category.builder().name("Main").build());
        Product product = productService.addProduct(Product.builder()
                .name("Antigravity ball")
                .price(100.0)
                .category(category)
                .build());

        Product found = productService.findById(product.getId());

        assertNotNull(found);
        assertEquals(product.getId(), found.getId());
        assertEquals("Antigravity ball", found.getName());
    }

    @Test
    @DisplayName("Should return all products")
    void testFindProducts() {
        Category category = categoryService.createCategory(Category.builder().name("Food").build());
        productService.addProduct(Product.builder().name("Space Milk").price(10.0).category(category).build());
        productService.addProduct(Product.builder().name("Space Bread").price(5.0).category(category).build());

        List<Product> products = productService.findProducts();

        assertNotNull(products);
        assertTrue(products.size() >= 2);
        assertTrue(products.stream().anyMatch(p -> p.getName().equals("Space Milk")));
        assertTrue(products.stream().anyMatch(p -> p.getName().equals("Space Bread")));
    }

    @Test
    @DisplayName("Should update an existing product")
    void testUpdateProduct() {
        Category category = categoryService.createCategory(Category.builder().name("Old Cat").build());
        Product original = productService.addProduct(Product.builder()
                .name("Old Helmet")
                .price(100.0)
                .category(category)
                .build());

        Product updateData = Product.builder()
                .name("Updated Helmet")
                .description("New version")
                .price(300.0)
                .category(category)
                .build();

        Product result = productService.updateProduct(original.getId(), updateData);

        assertEquals(original.getId(), result.getId());
        assertEquals("Updated Helmet", result.getName());
        assertEquals(300.0, result.getPrice());

        Product fetched = productService.findById(original.getId());
        assertEquals("Updated Helmet", fetched.getName());
    }

    @Test
    @DisplayName("Should delete product by ID")
    void testDeleteProduct() {
        Category category = categoryService.createCategory(Category.builder().name("Trash").build());
        Product product = productService.addProduct(Product.builder()
                .name("To Delete")
                .price(1.0)
                .category(category)
                .build());

        Long idToDelete = product.getId();

        productService.deleteProduct(idToDelete);

        assertThrows(ProductNotFoundException.class, () -> productService.findById(idToDelete));
    }

    @Test
    @DisplayName("Should throw exception when deleting non-existent product")
    void testDeleteNonExistentProduct() {
        assertThrows(ProductNotFoundException.class, () -> productService.deleteProduct(99999L));
    }
}