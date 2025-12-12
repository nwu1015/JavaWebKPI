package com.example.spacecatmarket.javawebkpi.service;

import com.example.spacecatmarket.javawebkpi.integration.AbstractIntegrationTest;
import com.example.spacecatmarket.javawebkpi.domain.Category;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@DisplayName("Category Service Integration Test")
class CategoryServiceTest extends AbstractIntegrationTest {

    @Autowired
    private CategoryService categoryService;

    @Test
    @DisplayName("Should create and return category with ID")
    void shouldCreateCategory() {
        Category newCategory = Category.builder()
                .name("Spaceships")
                .build();

        Category created = categoryService.createCategory(newCategory);

        assertThat(created.getId()).isNotNull();
        assertThat(created.getName()).isEqualTo("Spaceships");
    }

    @Test
    @DisplayName("Should return all categories")
    void shouldFindAll() {

        categoryService.createCategory(Category.builder().name("Cat1").build());
        categoryService.createCategory(Category.builder().name("Cat2").build());

        List<Category> all = categoryService.findAll();

        assertThat(all).hasSizeGreaterThanOrEqualTo(2);
    }
}