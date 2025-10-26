package com.example.spacecatmarket.javawebkpi.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    Long id;
    String name;
    String description;
    Double price;
    Category category;
}