package com.example.spacecatmarket.javawebkpi.domain;

import lombok.Builder;
import lombok.Value;

import java.util.List;

@Value
@Builder
public class Cart {
    Long id;
    Long customerId;
    List<OrderItem> products;
}