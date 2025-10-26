package com.example.spacecatmarket.javawebkpi.dto;

import com.example.spacecatmarket.javawebkpi.domain.Product;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductItemDto {
    Product product;
    int quantity;
}