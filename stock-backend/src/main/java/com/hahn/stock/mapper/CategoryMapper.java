package com.hahn.stock.mapper;

import com.hahn.stock.dto.CategoryDto;
import com.hahn.stock.entity.CategoryEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CategoryMapper {
    CategoryMapper INSTANCE = Mappers.getMapper(CategoryMapper.class);

    @Mapping(target = "productCount", expression = "java(category.getProducts() != null ? category.getProducts().size() : 0)")
    CategoryDto toDto(CategoryEntity category);

    @Mapping(target = "products", ignore = true)
    CategoryEntity toEntity(CategoryDto dto);
}