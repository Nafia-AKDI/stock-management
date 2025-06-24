package com.hahn.stock.service;

import com.hahn.stock.dto.ProductDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductService {

    ProductDto createProduct(ProductDto productDto);
    ProductDto getProductById(String id);
    List<ProductDto> getAllProducts();
    Page<ProductDto> getAllProducts(Pageable pageable);
    ProductDto updateProduct(String id, ProductDto productDto);
    void deleteProduct(String id);
    List<ProductDto> getProductsByCategory(String categoryId);
}
