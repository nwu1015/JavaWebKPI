package com.example.spacecatmarket.javawebkpi.service.impl;

import com.example.spacecatmarket.javawebkpi.domain.Product;
import com.example.spacecatmarket.javawebkpi.repository.CategoryRepository;
import com.example.spacecatmarket.javawebkpi.repository.ProductRepository;
import com.example.spacecatmarket.javawebkpi.repository.entity.CategoryEntity;
import com.example.spacecatmarket.javawebkpi.repository.entity.ProductEntity;
import com.example.spacecatmarket.javawebkpi.mapper.ProductMapper;
import com.example.spacecatmarket.javawebkpi.service.ProductService;
import com.example.spacecatmarket.javawebkpi.service.exception.ProductNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProductMapper productMapper;

    @Override
    @Transactional
    public Product addProduct(Product productDomain) {
        CategoryEntity categoryEntity = categoryRepository.findById(productDomain.getCategory().getId())
                .orElseThrow(() -> new RuntimeException("Category not found"));

        ProductEntity entity = productMapper.toEntity(productDomain);
        entity.setCategory(categoryEntity);

        ProductEntity savedEntity = productRepository.save(entity);

        return productMapper.toDomain(savedEntity);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Product> findProducts() {
        return productRepository.findAll().stream()
                .map(productMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Product findById(Long id) {
        return productRepository.findById(id)
                .map(productMapper::toDomain)
                .orElseThrow(() -> new ProductNotFoundException(id));
    }

    @Override
    @Transactional
    public Product updateProduct(Long id, Product productDomain) {
        ProductEntity existingEntity = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));

        existingEntity.setName(productDomain.getName());
        existingEntity.setDescription(productDomain.getDescription());
        existingEntity.setPrice(productDomain.getPrice());

        if (productDomain.getCategory() != null) {
            CategoryEntity newCategory = categoryRepository.findById(productDomain.getCategory().getId())
                    .orElseThrow(() -> new RuntimeException("Category not found"));
            existingEntity.setCategory(newCategory);
        }

        ProductEntity updatedEntity = productRepository.save(existingEntity);
        return productMapper.toDomain(updatedEntity);
    }

    @Override
    @Transactional
    public void deleteProduct(Long id) {
        if (!productRepository.existsById(id)) {
            throw new ProductNotFoundException(id);
        }
        productRepository.deleteById(id);
    }
}