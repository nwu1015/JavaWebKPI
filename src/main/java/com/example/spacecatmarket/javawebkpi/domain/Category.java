package com.example.spacecatmarket.javawebkpi.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Category {
    private Long id;
    private String name;
}

