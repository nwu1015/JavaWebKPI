package com.example.spacecatmarket.javawebkpi.service;

import com.example.spacecatmarket.javawebkpi.domain.Product;
import com.example.spacecatmarket.javawebkpi.dto.ProductDto;

import java.util.List;

public interface ProductService {
    Product addProduct(Product product);
    List<Product> findProducts();
    Product findById(Long id);
    Product updateProduct(Long id, Product product);
    void deleteProduct(Long id);
}

