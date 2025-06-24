package com.hahn.stock.controller;

import com.hahn.stock.dto.ProductDto;
import com.hahn.stock.service.ProductService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductControllerTest {

    @Mock
    private ProductService productService;

    @InjectMocks
    private ProductController productController;

    private ProductDto createSampleProduct() {
        return new ProductDto("1", "Laptop", "High performance laptop",
                BigDecimal.valueOf(999.99), 10, "cat1", "Electronics");
    }

    @Test
    void createProduct_ShouldReturnCreated() {
        ProductDto input = createSampleProduct();
        when(productService.createProduct(input)).thenReturn(input);

        ResponseEntity<ProductDto> response = productController.createProduct(input);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Laptop", response.getBody().getName());
        verify(productService).createProduct(input);
    }

    @Test
    void getProduct_ShouldReturnProduct() {
        ProductDto product = createSampleProduct();
        when(productService.getProductById("1")).thenReturn(product);

        ResponseEntity<ProductDto> response = productController.getProduct("1");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(BigDecimal.valueOf(999.99), response.getBody().getPrice());
    }

    @Test
    void getAllProducts_ShouldReturnAllProducts() {
        List<ProductDto> products = List.of(
                createSampleProduct(),
                new ProductDto("2", "Phone", "Smartphone", BigDecimal.valueOf(499.99), 5, "cat1", "Electronics")
        );
        when(productService.getAllProducts()).thenReturn(products);

        ResponseEntity<List<ProductDto>> response = productController.getAllProducts();

        assertEquals(2, response.getBody().size());
        assertEquals("Phone", response.getBody().get(1).getName());
    }

    @Test
    void getAllProductsPaged_ShouldReturnPagedProducts() {
        Page<ProductDto> page = new PageImpl<>(List.of(createSampleProduct()));
        when(productService.getAllProducts(any())).thenReturn(page);

        ResponseEntity<Page<ProductDto>> response =
                productController.getAllProducts(PageRequest.of(0, 10));

        assertEquals(1, response.getBody().getTotalElements());
        assertEquals("Laptop", response.getBody().getContent().get(0).getName());
    }

    @Test
    void getProductsByCategory_ShouldReturnFilteredProducts() {
        List<ProductDto> products = List.of(createSampleProduct());
        when(productService.getProductsByCategory("cat1")).thenReturn(products);

        ResponseEntity<List<ProductDto>> response =
                productController.getProductsByCategory("cat1");

        assertEquals(1, response.getBody().size());
        assertEquals("Electronics", response.getBody().get(0).getCategoryName());
    }

    @Test
    void updateProduct_ShouldReturnUpdatedProduct() {
        ProductDto updated = createSampleProduct();
        updated.setPrice(BigDecimal.valueOf(899.99));
        when(productService.updateProduct("1", updated)).thenReturn(updated);

        ResponseEntity<ProductDto> response =
                productController.updateProduct("1", updated);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(BigDecimal.valueOf(899.99), response.getBody().getPrice());
    }

    @Test
    void deleteProduct_ShouldReturnNoContent() {
        doNothing().when(productService).deleteProduct("1");

        ResponseEntity<Void> response = productController.deleteProduct("1");

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(productService).deleteProduct("1");
    }
}