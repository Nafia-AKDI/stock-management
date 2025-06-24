package com.hahn.stock.service;

import com.hahn.stock.dto.CategoryDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CategoryService {

    CategoryDto createCategory(CategoryDto categoryDto);
    CategoryDto getCategoryById(String id);
    List<CategoryDto> getAllCategories();
    Page<CategoryDto> getAllCategories(Pageable pageable);
    CategoryDto updateCategory(String id, CategoryDto categoryDto);
    void deleteCategory(String id);
}
