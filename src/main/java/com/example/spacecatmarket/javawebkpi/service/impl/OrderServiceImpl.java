package com.example.spacecatmarket.javawebkpi.service.impl;

import com.example.spacecatmarket.javawebkpi.domain.Order;
import com.example.spacecatmarket.javawebkpi.domain.OrderItem;
import com.example.spacecatmarket.javawebkpi.repository.OrderRepository;
import com.example.spacecatmarket.javawebkpi.repository.ProductRepository;
import com.example.spacecatmarket.javawebkpi.repository.entity.OrderEntity;
import com.example.spacecatmarket.javawebkpi.repository.entity.OrderItemEntity;
import com.example.spacecatmarket.javawebkpi.repository.entity.ProductEntity;
import com.example.spacecatmarket.javawebkpi.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    @Override
    @Transactional
    public Order createOrder(List<OrderItem> itemsDomain) {
        OrderEntity orderEntity = new OrderEntity();

        orderEntity.setOrderNumber(UUID.randomUUID().toString());

        double calculatedTotal = 0.0;

        for (OrderItem item : itemsDomain) {
            ProductEntity productEntity = productRepository.findById(item.getProduct().getId())
                    .orElseThrow(() -> new RuntimeException("Product not found"));

            OrderItemEntity itemEntity = OrderItemEntity.builder()
                    .order(orderEntity)
                    .product(productEntity)
                    .quantity(item.getQuantity())
                    .build();

            orderEntity.addItem(itemEntity);
            calculatedTotal += productEntity.getPrice() * item.getQuantity();
        }

        orderEntity.setTotalPrice(calculatedTotal);
        OrderEntity savedOrder = orderRepository.save(orderEntity);

        return new Order(
                savedOrder.getId(),
                savedOrder.getOrderNumber(),
                itemsDomain,
                savedOrder.getTotalPrice()
        );
    }
}