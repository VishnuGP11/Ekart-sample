package com.springboot.catalog_service.service;

import java.util.List;

import org.springframework.data.domain.PageRequest;

import com.springboot.catalog_service.dto.ProductDto;

public interface ProductService {

	Long addNewProduct(ProductDto productDto);

	ProductDto getProductById(Long product_id);

	List<ProductDto> getAllProducts();

	ProductDto updateProductsById(ProductDto productDto, Long product_id);

	void deleteProductById(Long product_id);

	List<ProductDto> getAllProductsByCategory(Long category_id);

	List<ProductDto> productsFilter(Long categoryId, Double min_price, Double max_price, String name,
			PageRequest pageRequest);

	List<ProductDto> productsSort(String name, PageRequest pageRequest);

	List<ProductDto> getProductsByIds(List<Long> productIds);

}
