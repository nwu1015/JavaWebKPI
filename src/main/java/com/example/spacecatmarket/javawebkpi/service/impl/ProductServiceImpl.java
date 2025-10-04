package com.example.spacecatmarket.javawebkpi.service.impl;

import com.example.spacecatmarket.javawebkpi.domain.Product;
import com.example.spacecatmarket.javawebkpi.dto.ProductDto;
import com.example.spacecatmarket.javawebkpi.mapper.ProductMapper;
import com.example.spacecatmarket.javawebkpi.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductMapper productMapper;
    private final AtomicLong id = new AtomicLong(0);
    private final ConcurrentHashMap<Long, Product> products = new ConcurrentHashMap<>();

    @Override
    public ProductDto addProduct(ProductDto product) {
        Product newProduct = productMapper.mapDtoToProduct(product);
        newProduct.setId(id.incrementAndGet());
        products.put(newProduct.getId(), newProduct);

        return productMapper.mapToDto(newProduct);
    }
}
