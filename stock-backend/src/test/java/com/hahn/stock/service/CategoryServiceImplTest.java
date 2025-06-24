package com.hahn.stock.service;

import com.hahn.stock.dto.CategoryDto;
import com.hahn.stock.entity.CategoryEntity;
import com.hahn.stock.entity.ProductEntity;
import com.hahn.stock.mapper.CategoryMapper;
import com.hahn.stock.repository.CategoryRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CategoryServiceImplTest {

    @Mock
    private CategoryRepository categoryRepository;

    @Spy
    private CategoryMapper mapper = CategoryMapper.INSTANCE;

    @InjectMocks
    private CategoryServiceImpl categoryService;

    @Test
    void createCategory_ShouldSuccess() {
        CategoryDto input = new CategoryDto();
        input.setName("Electronics");

        CategoryEntity entity = new CategoryEntity();
        entity.setName("Electronics");

        when(categoryRepository.existsByName("Electronics")).thenReturn(false);
        when(categoryRepository.save(any())).thenReturn(entity);

        CategoryDto result = categoryService.createCategory(input);

        assertNotNull(result);
        assertEquals("Electronics", result.getName());
        verify(categoryRepository, times(1)).save(any());
    }

    @Test
    void createCategory_ShouldThrowWhenNameExists() {
        CategoryDto input = new CategoryDto();
        input.setName("Electronics");

        when(categoryRepository.existsByName("Electronics")).thenReturn(true);

        assertThrows(IllegalArgumentException.class, () -> categoryService.createCategory(input));
    }

    @Test
    void getCategoryById_ShouldReturnCategory() {
        CategoryEntity entity = new CategoryEntity();
        entity.setId("1");
        entity.setName("Electronics");

        when(categoryRepository.findById("1")).thenReturn(Optional.of(entity));

        CategoryDto result = categoryService.getCategoryById("1");

        assertEquals("Electronics", result.getName());
    }

    @Test
    void getCategoryById_ShouldThrowWhenNotFound() {
        when(categoryRepository.findById("1")).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> categoryService.getCategoryById("1"));
    }

    @Test
    void getAllCategories_ShouldReturnAll() {
        CategoryEntity entity = new CategoryEntity();
        entity.setName("Electronics");

        when(categoryRepository.findAll()).thenReturn(List.of(entity));

        List<CategoryDto> result = categoryService.getAllCategories();

        assertEquals(1, result.size());
        assertEquals("Electronics", result.get(0).getName());
    }

    @Test
    void updateCategory_ShouldUpdate() {
        CategoryEntity existing = new CategoryEntity();
        existing.setId("1");
        existing.setName("Old Name");

        CategoryDto update = new CategoryDto();
        update.setName("New Name");

        when(categoryRepository.findById("1")).thenReturn(Optional.of(existing));
        when(categoryRepository.existsByName("New Name")).thenReturn(false);
        when(categoryRepository.save(any())).thenReturn(existing);

        CategoryDto result = categoryService.updateCategory("1", update);

        assertEquals("New Name", result.getName());
    }

    @Test
    void updateCategory_ShouldThrowWhenNameExists() {
        CategoryEntity existing = new CategoryEntity();
        existing.setId("1");
        existing.setName("Old Name");

        CategoryDto update = new CategoryDto();
        update.setName("Existing Name");

        when(categoryRepository.findById("1")).thenReturn(Optional.of(existing));
        when(categoryRepository.existsByName("Existing Name")).thenReturn(true);

        assertThrows(IllegalArgumentException.class,
                () -> categoryService.updateCategory("1", update));
    }

    @Test
    void deleteCategory_ShouldDelete() {
        CategoryEntity entity = new CategoryEntity();
        entity.setId("1");
        entity.setProducts(Collections.emptyList());

        when(categoryRepository.findById("1")).thenReturn(Optional.of(entity));
        doNothing().when(categoryRepository).delete(entity);

        assertDoesNotThrow(() -> categoryService.deleteCategory("1"));
        verify(categoryRepository, times(1)).delete(entity);
    }

    @Test
    void deleteCategory_ShouldThrowWhenHasProducts() {
        CategoryEntity entity = new CategoryEntity();
        entity.setId("1");
        entity.setProducts(List.of(new ProductEntity()));

        when(categoryRepository.findById("1")).thenReturn(Optional.of(entity));

        assertThrows(IllegalStateException.class,
                () -> categoryService.deleteCategory("1"));
    }
}