package com.example.spacecatmarket.javawebkpi.dto;

import com.example.spacecatmarket.javawebkpi.domain.Category;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductDto {
    String name;
    String description;
    Double price;
    Category category;
}
