package com.example.spacecatmarket.javawebkpi.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderItem {
    Product product;
    int quantity;
}