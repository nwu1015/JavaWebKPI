package com.example.spacecatmarket.javawebkpi.mapper;

import com.example.spacecatmarket.javawebkpi.domain.Category;
import com.example.spacecatmarket.javawebkpi.dto.CategoryDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    CategoryDto mapToDto(Category category);

    Category mapDtoToCategory(CategoryDto categoryDto);
}