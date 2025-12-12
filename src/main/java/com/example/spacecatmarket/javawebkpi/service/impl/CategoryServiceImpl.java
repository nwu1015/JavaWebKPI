package com.example.spacecatmarket.javawebkpi.service.impl;

import com.example.spacecatmarket.javawebkpi.domain.Category;
import com.example.spacecatmarket.javawebkpi.mapper.CategoryMapper;
import com.example.spacecatmarket.javawebkpi.repository.CategoryRepository;
import com.example.spacecatmarket.javawebkpi.repository.entity.CategoryEntity;
import com.example.spacecatmarket.javawebkpi.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    @Transactional(readOnly = true)
    public List<Category> findAll() {
        return categoryRepository.findAll().stream()
                .map(categoryMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public Category createCategory(Category category) {
        CategoryEntity entity = categoryMapper.toEntity(category);

        CategoryEntity saved = categoryRepository.save(entity);

        return categoryMapper.toDomain(saved);
    }
}