package com.example.spacecatmarket.javawebkpi.service;

import com.example.spacecatmarket.javawebkpi.dto.ProductDto;

import java.util.List;

public interface ProductService {
    ProductDto addProduct(ProductDto product);
    List<ProductDto> findProducts();
    ProductDto findById(Long id);
    ProductDto updateProduct(Long id, ProductDto product);
    void deleteProduct(Long id);
}

