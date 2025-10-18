package com.example.spacecatmarket.javawebkpi.service.impl;

import com.example.spacecatmarket.javawebkpi.domain.Category;
import com.example.spacecatmarket.javawebkpi.domain.Product;
import com.example.spacecatmarket.javawebkpi.mapper.CategoryMapper;
import com.example.spacecatmarket.javawebkpi.mapper.ProductMapper;
import com.example.spacecatmarket.javawebkpi.service.ProductService;
import com.example.spacecatmarket.javawebkpi.service.exception.ProductNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductMapper productMapper;
    private final CategoryMapper categoryMapper;
    private final AtomicLong id = new AtomicLong(1);
    private final ConcurrentHashMap<Long, Product> products = new ConcurrentHashMap<>();

    public ProductServiceImpl(ProductMapper productMapper, CategoryMapper categoryMapper) {
        this.productMapper = productMapper;
        this.categoryMapper = categoryMapper;
        initializeMockData();
    }

    private void initializeMockData() {
        createData(
                "Antigravity ball",
                "Antigravity balls of thread",
                100.0,
                new Category(1L, "Main"));
        createData(
                "Space milk",
                "Super duper wonderful wonderful extraordinary useful satisfying sweet milk",
                150.0,
                new Category(2L, "Main"));
    }

    private void createData(String name, String description, Double price, Category category) {
        Product product = Product.builder()
                .id(id.getAndIncrement())
                .name(name)
                .description(description)
                .price(price)
                .category(category).build();
        products.put(product.getId(), product);
    }

    @Override
    public Product addProduct(Product product) {
        long newId = id.incrementAndGet();
        product.setId(newId);
        products.put(newId, product);
        return product;
    }

    @Override
    public List<Product> findProducts() {
        return new ArrayList<>(products.values());
    }

    @Override
    public Product findById(Long id) {
        Product product = products.get(id);
        if (product == null) {
            throw new ProductNotFoundException(id);
        }
        return product;
    }

    @Override
    public Product updateProduct(Long id, Product product) {
        if (!products.containsKey(id)) {
            throw new ProductNotFoundException(id);
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