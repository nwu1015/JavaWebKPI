package com.example.spacecatmarket.javawebkpi.dto;

import com.example.spacecatmarket.javawebkpi.domain.ProductItem;
import lombok.Builder;
import lombok.Value;

import java.util.List;

@Value
@Builder
public class CartDto {
    Long customerId;
    List<ProductItem> products;
}