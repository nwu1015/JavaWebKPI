package com.example.spacecatmarket.javawebkpi.service;

import com.example.spacecatmarket.javawebkpi.domain.Category;

import java.util.List;

public interface CategoryService {

    List<Category> findAll();

    Category createCategory(Category category);
}