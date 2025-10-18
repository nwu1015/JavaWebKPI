package com.example.spacecatmarket.javawebkpi.dto;

import com.example.spacecatmarket.javawebkpi.domain.ProductItem;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class CartDto {
    Long customerId;
    List<ProductItem> products;
}