package com.example.spacecatmarket.javawebkpi.dto;

import com.example.spacecatmarket.javawebkpi.domain.OrderItem;
import lombok.Builder;
import lombok.Value;

import java.util.List;

@Value
@Builder
public class OrderDto {
    String orderNumber;
    List<OrderItem> products;
    Double totalPrice;
}