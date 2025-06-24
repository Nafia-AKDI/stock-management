package com.hahn.stock.service;

import com.hahn.stock.dto.ProductDto;
import com.hahn.stock.entity.CategoryEntity;
import com.hahn.stock.entity.ProductEntity;
import com.hahn.stock.mapper.ProductMapper;
import com.hahn.stock.repository.CategoryRepository;
import com.hahn.stock.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @Spy
    private ProductMapper productMapper = ProductMapper.INSTANCE;

    @InjectMocks
    private ProductServiceImpl productService;

    private ProductDto createSampleDto() {
        return new ProductDto("1", "Laptop", "High performance",
                BigDecimal.valueOf(999.99), 10, "cat1", "Electronics");
    }

    private ProductEntity createSampleEntity() {
        ProductEntity entity = new ProductEntity();
        entity.setId("1");
        entity.setName("Laptop");
        entity.setDescription("High performance");
        entity.setPrice(BigDecimal.valueOf(999.99));
        entity.setQuantity(10);
        entity.setCategory(CategoryEntity.builder().name("Electronics").description("des").build());
        return entity;
    }

    @Test
    void createProduct_ShouldSuccess() {
        ProductDto dto = createSampleDto();
        CategoryEntity category = CategoryEntity.builder().name("Electronics").description("Electronics").build();

        when(categoryRepository.findById("cat1")).thenReturn(Optional.of(category));
        when(productRepository.save(any())).thenReturn(createSampleEntity());

        ProductDto result = productService.createProduct(dto);

        assertNotNull(result);
        assertEquals("Laptop", result.getName());
        assertEquals("Electronics", result.getCategoryName());
        verify(productRepository).save(any());
    }

    @Test
    void createProduct_ShouldThrowWhenCategoryNotFound() {
        ProductDto dto = createSampleDto();
        when(categoryRepository.findById("cat1")).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> productService.createProduct(dto));
    }

    @Test
    void getProductById_ShouldReturnProduct() {
        when(productRepository.findById("1")).thenReturn(Optional.of(createSampleEntity()));

        ProductDto result = productService.getProductById("1");

        assertEquals("Laptop", result.getName());
        assertEquals(10, result.getQuantity());
    }

    @Test
    void getProductById_ShouldThrowWhenNotFound() {
        when(productRepository.findById("1")).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> productService.getProductById("1"));
    }

    @Test
    void getAllProducts_ShouldReturnAll() {
        when(productRepository.findAll()).thenReturn(List.of(createSampleEntity()));

        List<ProductDto> result = productService.getAllProducts();

        assertEquals(1, result.size());
        assertEquals(BigDecimal.valueOf(999.99), result.get(0).getPrice());
    }

    @Test
    void updateProduct_ShouldUpdateFields() {
        ProductEntity existing = createSampleEntity();
        ProductDto update = createSampleDto();
        update.setPrice(BigDecimal.valueOf(899.99));
        update.setQuantity(5);

        when(productRepository.findById("1")).thenReturn(Optional.of(existing));
        when(categoryRepository.findById("cat1")).thenReturn(Optional.of(existing.getCategory()));
        when(productRepository.save(any())).thenReturn(existing);

        ProductDto result = productService.updateProduct("1", update);

        assertEquals(BigDecimal.valueOf(899.99), result.getPrice());
        assertEquals(5, result.getQuantity());
    }

    @Test
    void updateProduct_ShouldThrowWhenProductNotFound() {
        ProductDto update = createSampleDto();
        when(productRepository.findById("1")).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> productService.updateProduct("1", update));
    }

    @Test
    void updateProduct_ShouldThrowWhenCategoryNotFound() {
        ProductEntity existing = createSampleEntity();
        ProductDto update = createSampleDto();

        when(productRepository.findById("1")).thenReturn(Optional.of(existing));
        when(categoryRepository.findById("cat1")).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> productService.updateProduct("1", update));
    }

    @Test
    void deleteProduct_ShouldDelete() {
        when(productRepository.findById("1")).thenReturn(Optional.of(createSampleEntity()));
        doNothing().when(productRepository).delete(any());

        assertDoesNotThrow(() -> productService.deleteProduct("1"));
        verify(productRepository).delete(any());
    }

    @Test
    void deleteProduct_ShouldThrowWhenNotFound() {
        when(productRepository.findById("1")).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> productService.deleteProduct("1"));
    }
}