package com.hahn.stock.controller;

import com.hahn.stock.dto.CategoryDto;
import com.hahn.stock.service.CategoryService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CategoryControllerTest {

    @Mock
    private CategoryService categoryService;

    @InjectMocks
    private CategoryController categoryController;

    @Test
    void createCategory_ShouldReturnCreated() {
        CategoryDto input = new CategoryDto();
        input.setName("Electronics");

        CategoryDto output = new CategoryDto();
        output.setId("1");
        output.setName("Electronics");

        when(categoryService.createCategory(input)).thenReturn(output);

        ResponseEntity<CategoryDto> response = categoryController.createCategory(input);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("1", Objects.requireNonNull(response.getBody()).getId());
        verify(categoryService, times(1)).createCategory(input);
    }

    @Test
    void getCategory_ShouldReturnCategory() {
        CategoryDto category = new CategoryDto();
        category.setId("1");
        category.setName("Electronics");

        when(categoryService.getCategoryById("1")).thenReturn(category);

        ResponseEntity<CategoryDto> response = categoryController.getCategory("1");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Electronics", Objects.requireNonNull(response.getBody()).getName());
    }

    @Test
    void getAllCategories_ShouldReturnAllCategories() {
        List<CategoryDto> categories = Arrays.asList(
                CategoryDto.builder().id("1").name("Electronics").build(),
                CategoryDto.builder().id("2").name("Clothing").build()
        );

        when(categoryService.getAllCategories()).thenReturn(categories);

        ResponseEntity<List<CategoryDto>> response = categoryController.getAllCategories();

        assertEquals(2, Objects.requireNonNull(response.getBody()).size());
        assertEquals("Clothing", response.getBody().get(1).getName());
    }

    @Test
    void getAllCategoriesPaged_ShouldReturnPagedCategories() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<CategoryDto> page = new PageImpl<>(List.of(
                CategoryDto.builder().id("1").name("Electronics").build()
        ));

        when(categoryService.getAllCategories(pageable)).thenReturn(page);

        ResponseEntity<Page<CategoryDto>> response = categoryController.getAllCategoriesPaged(pageable);

        assertEquals(1, Objects.requireNonNull(response.getBody()).getTotalElements());
        assertEquals("Electronics", response.getBody().getContent().get(0).getName());
    }

    @Test
    void updateCategory_ShouldReturnUpdatedCategory() {
        CategoryDto updateDto = new CategoryDto();
        updateDto.setName("Updated Electronics");

        CategoryDto updated = new CategoryDto();
        updated.setId("1");
        updated.setName("Updated Electronics");

        when(categoryService.updateCategory("1", updateDto)).thenReturn(updated);

        ResponseEntity<CategoryDto> response = categoryController.updateCategory("1", updateDto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Updated Electronics", Objects.requireNonNull(response.getBody()).getName());
    }

    @Test
    void deleteCategory_ShouldReturnNoContent() {
        doNothing().when(categoryService).deleteCategory("1");

        ResponseEntity<Void> response = categoryController.deleteCategory("1");

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(categoryService, times(1)).deleteCategory("1");
    }
}