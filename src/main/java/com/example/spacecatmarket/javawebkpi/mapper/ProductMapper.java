package com.example.spacecatmarket.javawebkpi.mapper;

import com.example.spacecatmarket.javawebkpi.domain.Product;
import com.example.spacecatmarket.javawebkpi.dto.ProductDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    ProductDto mapToDto(Product product);

    Product mapDtoToProduct(ProductDto productDto);
}