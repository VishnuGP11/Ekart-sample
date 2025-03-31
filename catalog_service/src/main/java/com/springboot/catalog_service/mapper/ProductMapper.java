package com.springboot.catalog_service.mapper;

import java.util.List;
import java.util.stream.Collectors;

import com.springboot.catalog_service.dto.ProductAttrDto;
import com.springboot.catalog_service.dto.ProductDto;
import com.springboot.catalog_service.entity.Category;
import com.springboot.catalog_service.entity.Product;
import com.springboot.catalog_service.entity.ProductAttr;

public class ProductMapper {

	public static Product mapToproduct(ProductDto productDto) {
		Product product = new Product();
		product.setProductId(productDto.getProductId());
		product.setName(productDto.getName());
		product.setDescription(productDto.getDescription());
		product.setPrice(productDto.getPrice());
		product.setStockQuantity(productDto.getStockQuantity());
		product.setImg_url(productDto.getImgUrl());
		product.setSku(productDto.getSku());
		product.setIsActive(productDto.getIsActive());
		if (productDto.getCategoryId() != null) {
			Category category = new Category();
			category.setCategoryId(productDto.getCategoryId());
			product.setCategory(category);
		}
		if (productDto.getAttributes() != null) {
			product.setAttributes(ProductMapper.convertToEntity(productDto.getAttributes()));
		} else {
			product.setAttributes(null);
		}
		return product;

	}

	public static List<ProductAttr> convertToEntity(List<ProductAttrDto> productAttrs) {
		return productAttrs.stream().map(productAttr -> {
			ProductAttr attr = new ProductAttr();
			attr.setProductAttrId(productAttr.getProductAttrId());
			attr.setAttributeName(productAttr.getAttributeName());
			attr.setAttributeValue(productAttr.getAttributeValue());
			return attr;
		}).collect(Collectors.toList()); // Collect the results into a List<ProductDTO>
	}

	public static ProductDto mapToProductDto(Product product) {
		ProductDto productDto = new ProductDto();
		productDto.setProductId(product.getProductId());
		productDto.setName(product.getName());
		productDto.setDescription(product.getDescription());
		productDto.setPrice(product.getPrice());
		productDto.setStockQuantity(product.getStockQuantity());
		productDto.setImgUrl(product.getImg_url());
		productDto.setIsActive(product.getIsActive());
		productDto.setSku(product.getSku());
		productDto.setCategoryId(product.getCategory().getCategoryId());
		if (product.getAttributes() != null) {
			productDto.setAttributes(ProductMapper.convertToDto(product.getAttributes()));
		} else {
			productDto.setAttributes(null);
		}
		return productDto;

	}

	public static List<ProductAttrDto> convertToDto(List<ProductAttr> productAttrs) {
		return productAttrs.stream().map(productAttr -> {
			ProductAttrDto attr = new ProductAttrDto();
			attr.setProductAttrId(productAttr.getProductAttrId());
			attr.setAttributeName(productAttr.getAttributeName());
			attr.setAttributeValue(productAttr.getAttributeValue());
			return attr;
		}).collect(Collectors.toList());
	}
}
