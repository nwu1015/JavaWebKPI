package com.example.spacecatmarket.javawebkpi.web;

import com.example.spacecatmarket.javawebkpi.domain.Product;
import com.example.spacecatmarket.javawebkpi.dto.ProductDto;
import com.example.spacecatmarket.javawebkpi.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<ProductDto> createProduct(@RequestBody ProductDto product) {
        return new ResponseEntity<>(productService.addProduct(product), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ProductDto>> getProducts() {
        return null;
    }
}
