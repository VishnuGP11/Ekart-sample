package com.springboot.catalog_service.serviceImpl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.springboot.catalog_service.dto.ProductDto;
import com.springboot.catalog_service.entity.Category;
import com.springboot.catalog_service.entity.Product;
import com.springboot.catalog_service.exception.ResourceNotFoundException;
import com.springboot.catalog_service.mapper.ProductMapper;
import com.springboot.catalog_service.repository.CategoryRepository;
import com.springboot.catalog_service.repository.ProductRepository;
import com.springboot.catalog_service.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private CategoryRepository categoryRepository;

	@Override
	public Long addNewProduct(ProductDto productDto) {
		Category category = categoryRepository.findById(productDto.getCategoryId())
				.orElseThrow(() -> new ResourceNotFoundException("Category id not found"));
		Product product = ProductMapper.mapToproduct(productDto);
		if (category.getCategoryId() != null) {
			productDto.setCategoryId(category.getCategoryId());
		}
		Product savedProduct = productRepository.save(product);
		return savedProduct.getProductId();
	}

	@Override
	public ProductDto getProductById(Long product_id) {
		try {
			Product product = productRepository.findById(product_id)
					.orElseThrow(() -> new ResourceNotFoundException("Product id not found"));
			return ProductMapper.mapToProductDto(product);
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public List<ProductDto> getAllProducts() {
		List<Product> allProducts = productRepository.findAll();
		return allProducts.stream().map(product -> ProductMapper.mapToProductDto(product)).collect(Collectors.toList());
	}

	@Override
	public ProductDto updateProductsById(ProductDto productDto, Long product_id) {
		Product newProduct = ProductMapper.mapToproduct(productDto);
		Product existingProduct = productRepository.findById(product_id)
				.orElseThrow(() -> new ResourceNotFoundException("Product id not found"));
		if (newProduct.getName() != null) {
			existingProduct.setName(newProduct.getName());
		}
		if (newProduct.getDescription() != null) {
			existingProduct.setDescription(newProduct.getDescription());
		}
		if (newProduct.getPrice() != 0.0) {
			existingProduct.setPrice(newProduct.getPrice());
		}
		if (newProduct.getStockQuantity() != 0) {
			existingProduct.setStockQuantity(newProduct.getStockQuantity());
		}
		if (newProduct.getImg_url() != null) {
			existingProduct.setImg_url(newProduct.getImg_url());
		}
		if (newProduct.getSku() != null) {
			existingProduct.setSku(newProduct.getSku());
		}
		Category category = categoryRepository.findById(newProduct.getCategory().getCategoryId())
				.orElseThrow(() -> new ResourceNotFoundException("Category id not found"));
		if (category != null) {
			existingProduct.setCategory(category);
		}
		Product updatedProduct = productRepository.save(existingProduct);
		return ProductMapper.mapToProductDto(updatedProduct);
	}

	@Override
	public void deleteProductById(Long product_id) {
		Product product = productRepository.findById(product_id)
				.orElseThrow(() -> new ResourceNotFoundException("Product id not found"));
		productRepository.delete(product);
	}

	@Override
	public List<ProductDto> getAllProductsByCategory(Long category_id) {
		Category category = categoryRepository.findById(category_id)
				.orElseThrow(() -> new ResourceNotFoundException("Category id not found"));
		List<Product> allProducts = productRepository.findByCategory(category);
		return allProducts.stream().map(product -> ProductMapper.mapToProductDto(product)).collect(Collectors.toList());
	}

	@Override
	public List<ProductDto> productsSort(String name, PageRequest pageRequest) {
		List<Product> products = productRepository.findByNameContaining(name, pageRequest);
		return products.stream().map(product -> ProductMapper.mapToProductDto(product)).collect(Collectors.toList());
	}

	@Override
	public List<ProductDto> productsFilter(Long categoryId, Double min_price, Double max_price, String name,
			PageRequest pageRequest) {
		List<Product> products = productRepository.findByCategory_CategoryIdAndPriceBetweenAndNameContaining(categoryId,
				min_price, max_price, name, pageRequest);
		return products.stream().map(product -> ProductMapper.mapToProductDto(product)).collect(Collectors.toList());
	}

	@Override
	public List<ProductDto> getProductsByIds(List<Long> productIds) {
		List<Product> products = productRepository.findProductByProductIds(productIds);
		return products.stream().map(product -> ProductMapper.mapToProductDto(product)).collect(Collectors.toList());
	}
}
