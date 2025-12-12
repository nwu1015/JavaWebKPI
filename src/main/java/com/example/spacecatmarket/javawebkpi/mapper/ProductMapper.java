package com.example.spacecatmarket.javawebkpi.mapper;

import com.example.spacecatmarket.javawebkpi.domain.Product;
import com.example.spacecatmarket.javawebkpi.dto.ProductDto;
import com.example.spacecatmarket.javawebkpi.repository.entity.ProductEntity;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {CategoryMapper.class}, builder = @Builder(disableBuilder = true))
public interface ProductMapper {
    ProductDto toProductDto(Product product);

    Product toProduct(ProductDto productDto);

    ProductEntity toEntity(Product product);
    Product toDomain(ProductEntity entity);
}