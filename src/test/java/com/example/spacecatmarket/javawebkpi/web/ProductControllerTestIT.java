package com.example.spacecatmarket.javawebkpi.web;

import com.example.spacecatmarket.javawebkpi.domain.Category;
import com.example.spacecatmarket.javawebkpi.domain.Product;
import com.example.spacecatmarket.javawebkpi.dto.ProductDto;
import com.example.spacecatmarket.javawebkpi.mapper.ProductMapper;
import com.example.spacecatmarket.javawebkpi.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("Product Controller Tests")
@WithMockUser(username = "tester", roles = "USER")
public class ProductControllerTestIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private ProductService productService;

    @MockitoBean
    private ProductMapper productMapper;

    private ProductDto productDtoRequest;
    private ProductDto productDtoResponse;
    private Product productEntity;
    private Category testCategory;

    private static final Long TEST_PRODUCT_ID = 1L;
    private static final String TEST_PRODUCT_NAME = "Galaxy Helmet";
    private static final String TEST_PRODUCT_DESCRIPTION = "Galaxy Helmet Description";
    private static final Double TEST_PRODUCT_PRICE = 1.5;

    private static final Long TEST_CATEGORY_ID = 1L;
    private static final String TEST_CATEGORY_NAME = "Test Category";

    @BeforeEach
    void setUp() {
        reset(productService, productMapper);
        testCategory = Category.builder().id(TEST_CATEGORY_ID).name(TEST_CATEGORY_NAME).build();

        productDtoResponse = ProductDto.builder()
                .id(TEST_PRODUCT_ID)
                .name(TEST_PRODUCT_NAME)
                .description(TEST_PRODUCT_DESCRIPTION)
                .price(TEST_PRODUCT_PRICE)
                .category(testCategory)
                .build();

        productDtoRequest = ProductDto.builder()
                .name(TEST_PRODUCT_NAME)
                .description(TEST_PRODUCT_DESCRIPTION)
                .price(TEST_PRODUCT_PRICE)
                .category(testCategory)
                .build();

        productEntity = Product.builder()
                .id(TEST_PRODUCT_ID)
                .name(TEST_PRODUCT_NAME)
                .description(TEST_PRODUCT_DESCRIPTION)
                .price(TEST_PRODUCT_PRICE)
                .category(testCategory)
                .build();
    }

    @Test
    @SneakyThrows
    @DisplayName("Should return all products")
    void testGetProductsSuccess() {
        when(productService.findProducts()).thenReturn(List.of(productEntity));
        when(productMapper.toProductDto(any(Product.class))).thenReturn(productDtoResponse);

        mockMvc.perform(get("/api/v1/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name").value(productDtoResponse.getName()));

        verify(productService, times(1)).findProducts();
    }

    @Test
    @SneakyThrows
    @DisplayName("Should return product by id")
    void testGetProductByIdSuccess() {
        when(productService.findById(anyLong())).thenReturn(productEntity);
        when(productMapper.toProductDto(any(Product.class))).thenReturn(productDtoResponse);

        mockMvc.perform(get("/api/v1/products/{id}", TEST_PRODUCT_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(productDtoResponse.getName()))
                .andExpect(jsonPath("$.id").value(TEST_PRODUCT_ID));

        verify(productService, times(1)).findById(anyLong());
    }

    @Test
    @SneakyThrows
    @DisplayName("Should return that this product don't exist (status 404)")
    void testGetProductByIdNotFound() {
        when(productService.findById(anyLong())).thenReturn(null);

        mockMvc.perform(get("/api/v1/products/{id}", 99L))
                .andExpect(status().isNotFound());

        verify(productService, times(1)).findById(anyLong());
        verify(productMapper, never()).toProductDto(any());
    }

    @Test
    @SneakyThrows
    @DisplayName("Should create a product")
    void testCreateProductSuccess() {
        when(productMapper.toProduct(any(ProductDto.class))).thenReturn(productEntity);
        when(productService.addProduct(any(Product.class))).thenReturn(productEntity);
        when(productMapper.toProductDto(any(Product.class))).thenReturn(productDtoResponse);

        mockMvc.perform(post("/api/v1/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productDtoRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value(productDtoResponse.getName()));

        verify(productService, times(1)).addProduct(any(Product.class));
    }

    @Test
    @SneakyThrows
    @DisplayName("Should return error. Validation check while user create a product")
    void testCreateProductInvalidData() {
        ProductDto invalidRequest = ProductDto.builder()
                .name("a")
                .price(-10.0)
                .category(null)
                .build();

        mockMvc.perform(post("/api/v1/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest());

        verify(productService, never()).addProduct(any());
    }

    @Test
    @SneakyThrows
    @DisplayName("Should update a product")
    void testUpdateProductSuccess() {
        when(productMapper.toProduct(any(ProductDto.class))).thenReturn(productEntity);
        when(productService.updateProduct(eq(TEST_PRODUCT_ID), any(Product.class))).thenReturn(productEntity);
        when(productMapper.toProductDto(any(Product.class))).thenReturn(productDtoResponse);

        mockMvc.perform(put("/api/v1/products/{id}", TEST_PRODUCT_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productDtoRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(TEST_PRODUCT_ID))
                .andExpect(jsonPath("$.name").value(productDtoResponse.getName()));

        verify(productService, times(1)).updateProduct(eq(TEST_PRODUCT_ID), any(Product.class));
    }

    @Test
    @SneakyThrows
    @DisplayName("Should delete a product")
    void testDeleteProductSuccess() {
        doNothing().when(productService).deleteProduct(anyLong());

        mockMvc.perform(delete("/api/v1/products/{id}", TEST_PRODUCT_ID))
                .andExpect(status().isNoContent());

        verify(productService, times(1)).deleteProduct(anyLong());
    }
}