package com.example.spacecatmarket.javawebkpi.repository;

import com.example.spacecatmarket.javawebkpi.integration.AbstractIntegrationTest;
import com.example.spacecatmarket.javawebkpi.repository.entity.CategoryEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
class CategoryRepositoryIT extends AbstractIntegrationTest {

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    @DisplayName("Should find category by name")
    void shouldFindByName() {
        CategoryEntity category = CategoryEntity.builder()
                .name("Exotic Food")
                .build();
        categoryRepository.save(category);

        Optional<CategoryEntity> found = categoryRepository.findByName("Exotic Food");

        assertThat(found).isPresent();
        assertThat(found.get().getName()).isEqualTo("Exotic Food");
    }

    @Test
    @DisplayName("Should return empty when category name not found")
    void shouldReturnEmptyIfNotFound() {
        Optional<CategoryEntity> found = categoryRepository.findByName("NonExistent");
        assertThat(found).isEmpty();
    }
}