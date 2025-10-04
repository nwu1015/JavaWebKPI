package com.example.spacecatmarket.javawebkpi.dto;

import com.example.spacecatmarket.javawebkpi.domain.Category;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {
    Long id;
    String name;
    String description;
    Double price;
    Category category;
}
