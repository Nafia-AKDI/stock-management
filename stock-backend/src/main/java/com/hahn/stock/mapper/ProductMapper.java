package com.hahn.stock.mapper;

import com.hahn.stock.dto.ProductDto;
import com.hahn.stock.entity.ProductEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    @Mapping(target = "categoryId", source = "category.id")
    @Mapping(target = "categoryName", source = "category.name")
    ProductDto toDto(ProductEntity product);

    @Mapping(target = "category", ignore = true)
    ProductEntity toEntity(ProductDto productDto);
}