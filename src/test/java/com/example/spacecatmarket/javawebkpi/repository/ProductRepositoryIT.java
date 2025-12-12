package com.example.spacecatmarket.javawebkpi.repository;

import com.example.spacecatmarket.javawebkpi.integration.AbstractIntegrationTest;
import com.example.spacecatmarket.javawebkpi.repository.entity.CategoryEntity;
import com.example.spacecatmarket.javawebkpi.repository.entity.OrderEntity;
import com.example.spacecatmarket.javawebkpi.repository.entity.OrderItemEntity;
import com.example.spacecatmarket.javawebkpi.repository.entity.ProductEntity;
import com.example.spacecatmarket.javawebkpi.repository.projection.TopProductProjection;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
class ProductRepositoryIT extends AbstractIntegrationTest {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private OrderRepository orderRepository;

    @Test
    @DisplayName("Should find top selling products projection")
    void shouldFindTopSellingProducts() {
        CategoryEntity category = categoryRepository.save(CategoryEntity.builder().name("Food").build());

        ProductEntity milk = productRepository.save(ProductEntity.builder()
                .name("Space Milk").price(10.0).category(category).build());

        ProductEntity bread = productRepository.save(ProductEntity.builder()
                .name("Space Bread").price(5.0).category(category).build());

        OrderEntity order = new OrderEntity();

        order.addItem(OrderItemEntity.builder().order(order).product(milk).quantity(5).build());
        order.addItem(OrderItemEntity.builder().order(order).product(bread).quantity(2).build());

        orderRepository.save(order);

        List<TopProductProjection> result = productRepository.findTopSellingProducts();

        assertThat(result).isNotEmpty();
        assertThat(result.get(0).getProductName()).isEqualTo("Space Milk");
        assertThat(result.get(0).getTotalSold()).isEqualTo(5L);
    }
}