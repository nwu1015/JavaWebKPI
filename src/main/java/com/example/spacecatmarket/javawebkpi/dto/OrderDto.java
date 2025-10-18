package com.example.spacecatmarket.javawebkpi.dto;

import com.example.spacecatmarket.javawebkpi.domain.Product;
import lombok.Builder;
import lombok.Value;

import java.util.List;

@Value
@Builder
public class OrderDto {
    List<Product> products;
    Double totalPrice;
}