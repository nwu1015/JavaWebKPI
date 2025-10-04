package com.example.spacecatmarket.javawebkpi.service.impl;

import com.example.spacecatmarket.javawebkpi.domain.Category;
import com.example.spacecatmarket.javawebkpi.dto.ProductDto;
import com.example.spacecatmarket.javawebkpi.mapper.CategoryMapper;
import com.example.spacecatmarket.javawebkpi.mapper.ProductMapper;
import com.example.spacecatmarket.javawebkpi.service.ProductService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

// TODO: hide ID
@Service
public class ProductServiceImpl implements ProductService {
    private final ProductMapper productMapper;
    private final CategoryMapper categoryMapper;
    private final AtomicLong id = new AtomicLong(1);
    private final ConcurrentHashMap<Long, ProductDto> products = new ConcurrentHashMap<>();

    public ProductServiceImpl(ProductMapper productMapper, CategoryMapper categoryMapper) {
        this.productMapper = productMapper;
        this.categoryMapper = categoryMapper;
        initializeMockData();
    }

    private void initializeMockData() {
        createData("Antigravity ball", "Antigravity balls of thread", 100.0, new Category(1L, "Main"));
        createData("Space milk", "Super duper wonderful wonderful extraordinary useful satisfying sweet milk", 150.0, new Category(2L, "Main"));
    }

    private void createData(String name, String description, Double price, Category category) {
        ProductDto productDto = ProductDto.builder().id(id.getAndIncrement()).name(name).description(description).price(price).category(category).build();
        products.put(productDto.getId(), productDto);
    }

    @Override
    public ProductDto addProduct(ProductDto product) {
        long newId = id.incrementAndGet();
        product.setId(newId);
        products.put(newId, product);
        return product;
    }

    @Override
    public List<ProductDto> findProducts() {
        return new ArrayList<>(products.values());
    }

    @Override
    public ProductDto findById(Long id) {
        return products.get(id);
    }

    @Override
    public ProductDto updateProduct(Long id, ProductDto product) {
        if (!products.containsKey(id)) {
            return addProduct(product);
        }
        product.setId(id);
        products.put(id, product);
        return product;
    }

    @Override
    public void deleteProduct(Long id) {
        products.remove(id);
    }
}