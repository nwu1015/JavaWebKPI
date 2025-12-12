package com.example.spacecatmarket.javawebkpi.domain;

import lombok.*;

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