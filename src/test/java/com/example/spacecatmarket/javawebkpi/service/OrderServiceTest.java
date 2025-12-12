package com.example.spacecatmarket.javawebkpi.service;

import com.example.spacecatmarket.javawebkpi.integration.AbstractIntegrationTest;
import com.example.spacecatmarket.javawebkpi.domain.Category;
import com.example.spacecatmarket.javawebkpi.domain.Order;
import com.example.spacecatmarket.javawebkpi.domain.OrderItem;
import com.example.spacecatmarket.javawebkpi.domain.Product;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@DisplayName("Order Service Integration Test")
class OrderServiceTest extends AbstractIntegrationTest {

    @Autowired
    private OrderService orderService;
    @Autowired
    private ProductService productService;
    @Autowired
    private CategoryService categoryService;

    @Test
    @DisplayName("Should create order, calculate total price and generate Natural ID")
    void shouldProcessOrderCorrectly() {
        Category cat = categoryService.createCategory(Category.builder().name("Engines").build());

        Product engine = productService.addProduct(Product.builder()
                .name("Hyperdrive X1")
                .price(1000.0)
                .category(cat).build());

        Product coolant = productService.addProduct(Product.builder()
                .name("Space Coolant")
                .price(50.0)
                .category(cat).build());

        List<OrderItem> items = List.of(
                OrderItem.builder().product(engine).quantity(1).build(), // 1000 * 1 = 1000
                OrderItem.builder().product(coolant).quantity(4).build() // 50 * 4 = 200
        );

        Order result = orderService.createOrder(items);

        assertThat(result.getId()).isNotNull();

        assertThat(result.getOrderNumber()).isNotNull();
        assertThat(result.getOrderNumber()).isNotEmpty();

        assertThat(result.getTotalPrice()).isEqualTo(1200.0);

        assertThat(result.getProducts()).hasSize(2);
    }
}