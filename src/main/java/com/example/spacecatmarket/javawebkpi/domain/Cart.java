package com.example.spacecatmarket.javawebkpi.domain;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class Cart {
    Long id;
    Long customerId;
    List<ProductItem> products;
}