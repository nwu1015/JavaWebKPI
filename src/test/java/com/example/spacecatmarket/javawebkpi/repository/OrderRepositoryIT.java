package com.example.spacecatmarket.javawebkpi.repository;

import com.example.spacecatmarket.javawebkpi.integration.AbstractIntegrationTest;
import com.example.spacecatmarket.javawebkpi.repository.entity.CategoryEntity;
import com.example.spacecatmarket.javawebkpi.repository.entity.OrderEntity;
import com.example.spacecatmarket.javawebkpi.repository.entity.OrderItemEntity;
import com.example.spacecatmarket.javawebkpi.repository.entity.ProductEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
class OrderRepositoryIT extends AbstractIntegrationTest {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    @DisplayName("Should save order with items and find by Natural ID")
    void shouldSaveAndFindOrder() {
        CategoryEntity category = categoryRepository.save(CategoryEntity.builder().name("TestCat").build());
        ProductEntity product = productRepository.save(ProductEntity.builder()
                .name("TestProduct")
                .price(100.0)
                .category(category)
                .build());

        String naturalId = UUID.randomUUID().toString();
        OrderEntity order = new OrderEntity();
        order.setOrderNumber(naturalId);
        order.setTotalPrice(200.0);

        OrderItemEntity item = OrderItemEntity.builder()
                .order(order)
                .product(product)
                .quantity(2)
                .build();
        order.addItem(item);

        orderRepository.save(order);

        var foundOrder = orderRepository.findByOrderNumber(naturalId);

        assertThat(foundOrder).isPresent();
        assertThat(foundOrder.get().getItems()).hasSize(1);
        assertThat(foundOrder.get().getItems().get(0).getProduct().getName()).isEqualTo("TestProduct");
    }
}