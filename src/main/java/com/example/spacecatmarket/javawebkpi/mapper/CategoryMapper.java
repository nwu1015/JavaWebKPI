package com.example.spacecatmarket.javawebkpi.mapper;

import com.example.spacecatmarket.javawebkpi.domain.Category;
import com.example.spacecatmarket.javawebkpi.dto.CategoryDto;
import com.example.spacecatmarket.javawebkpi.repository.entity.CategoryEntity;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", builder = @Builder(disableBuilder = true))
public interface CategoryMapper {
    CategoryDto mapToDto(Category category);

    Category mapDtoToCategory(CategoryDto categoryDto);

    CategoryEntity toEntity(Category category);
    Category toDomain(CategoryEntity entity);
}