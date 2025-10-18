package com.example.spacecatmarket.javawebkpi.domain;

import lombok.Value;

import java.util.List;

@Value
public class Order {
    Long id;
    List<Product> products;
    Double totalPrice;
}