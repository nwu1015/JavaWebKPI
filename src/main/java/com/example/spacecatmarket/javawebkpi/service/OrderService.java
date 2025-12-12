package com.example.spacecatmarket.javawebkpi.service;

import com.example.spacecatmarket.javawebkpi.domain.Order;
import com.example.spacecatmarket.javawebkpi.domain.OrderItem;
import java.util.List;

public interface OrderService {
    Order createOrder(List<OrderItem> items);
}