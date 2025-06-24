package com.hahn.stock.service;

import com.hahn.stock.dto.CategoryDto;
import com.hahn.stock.entity.CategoryEntity;
import com.hahn.stock.mapper.CategoryMapper;
import com.hahn.stock.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper mapper = CategoryMapper.INSTANCE;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    @Transactional
    public CategoryDto createCategory(CategoryDto categoryDto) {
        if (categoryRepository.existsByName(categoryDto.getName())) {
            throw new IllegalArgumentException("Category with name '" + categoryDto.getName() + "' already exists");
        }
        CategoryEntity saved = categoryRepository.save(mapper.toEntity(categoryDto));
        return mapper.toDto(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public CategoryDto getCategoryById(String id) {
        return categoryRepository.findById(id)
                .map(mapper::toDto)
                .orElseThrow(() -> new RuntimeException("Category not found with id: " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<CategoryDto> getAllCategories() {
        return categoryRepository.findAll().stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CategoryDto> getAllCategories(Pageable pageable) {
        return categoryRepository.findAll(pageable)
                .map(mapper::toDto);
    }

    @Override
    @Transactional
    public CategoryDto updateCategory(String id, CategoryDto categoryDto) {
        CategoryEntity category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found with id: " + id));

        if (!category.getName().equals(categoryDto.getName())) {
            if (categoryRepository.existsByName(categoryDto.getName())) {
                throw new IllegalArgumentException("Category with name '" + categoryDto.getName() + "' already exists");
            }
            category.setName(categoryDto.getName());
        }

        category.setDescription(categoryDto.getDescription());
        CategoryEntity updated = categoryRepository.save(category);
        return mapper.toDto(updated);
    }

    @Override
    @Transactional
    public void deleteCategory(String id) {
        CategoryEntity category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found with id: " + id));

        if (!category.getProducts().isEmpty()) {
            throw new IllegalStateException("Cannot delete category with associated products");
        }
        categoryRepository.delete(category);
    }
}