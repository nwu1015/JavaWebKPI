package com.example.spacecatmarket.javawebkpi.domain;

import lombok.Value;

import java.util.List;

@Value
public class Order {
    Long id;
    String orderNumber;
    List<OrderItem> products;
    Double totalPrice;
}