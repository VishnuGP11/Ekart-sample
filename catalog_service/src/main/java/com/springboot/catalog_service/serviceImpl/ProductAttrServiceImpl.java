package com.springboot.catalog_service.serviceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.catalog_service.dto.ProductAttrDto;
import com.springboot.catalog_service.entity.Product;
import com.springboot.catalog_service.entity.ProductAttr;
import com.springboot.catalog_service.exception.ResourceNotFoundException;
import com.springboot.catalog_service.mapper.ProductAttrMapper;
import com.springboot.catalog_service.repository.ProductAttrRepository;
import com.springboot.catalog_service.repository.ProductRepository;
import com.springboot.catalog_service.service.ProductAttrService;

@Service
public class ProductAttrServiceImpl implements ProductAttrService {

	@Autowired
	private ProductAttrRepository productAttrRepository;

	@Autowired
	private ProductRepository productRepository;

	public ProductAttrServiceImpl(ProductAttrRepository productAttrRepository, ProductRepository productRepository) {
		this.productAttrRepository = productAttrRepository;
		this.productRepository = productRepository;
	}

	@Override
	public ProductAttrDto addNewAttributes(ProductAttrDto productAttrDto, Long productId) {

		ProductAttr productAttr = ProductAttrMapper.mapToProductAttr(productAttrDto);
		Product product = productRepository.findById(productId)
				.orElseThrow(() -> new ResourceNotFoundException("product id not found"));
		productAttr.setProduct(product);
		ProductAttr savedAttr = productAttrRepository.save(productAttr);
		return ProductAttrMapper.mapToProductAttrDto(savedAttr);

	}

	@Override
	public ProductAttrDto getAttributeById(Long productId, Long attrId) {
		Product product = productRepository.findById(productId)
				.orElseThrow(() -> new ResourceNotFoundException("product id not found"));
		ProductAttr attr = null;
		if (product != null) {
			attr = productAttrRepository.findById(attrId)
					.orElseThrow(() -> new ResourceNotFoundException("product attr id not found"));

		}
		return ProductAttrMapper.mapToProductAttrDto(attr);
	}

	@Override
	public List<ProductAttrDto> getAllAttributes(Long productId) {
		Product product = productRepository.findById(productId)
				.orElseThrow(() -> new ResourceNotFoundException("product id not found"));
		List<ProductAttr> attributes = new ArrayList<ProductAttr>();
		if (product != null) {
			attributes = productAttrRepository.findByProduct_ProductId(productId);
		}
		return attributes.stream().map(attr -> ProductAttrMapper.mapToProductAttrDto(attr))
				.collect(Collectors.toList());
	}

	@Override
	public ProductAttrDto updateAttributeById(ProductAttrDto productAttrDto, Long productId, Long attrId) {
		Product product = productRepository.findById(productId)
				.orElseThrow(() -> new ResourceNotFoundException("product id not found"));
		ProductAttr existingAttr = productAttrRepository.findById(attrId)
				.orElseThrow(() -> new ResourceNotFoundException("product attr id not found"));
		ProductAttr newAttr = ProductAttrMapper.mapToProductAttr(productAttrDto);
		existingAttr.setProduct(product);
		if (product != null) {
			if (newAttr.getAttributeName() != null) {
				existingAttr.setAttributeName(newAttr.getAttributeName());
			}
			if (newAttr.getAttributeValue() != null) {
				existingAttr.setAttributeValue(newAttr.getAttributeValue());
			}
			if (newAttr.getProduct() != null) {
				existingAttr.getProduct().setProductId(productId);
			}
		}

		ProductAttr updatedAttr = productAttrRepository.save(existingAttr);
		return ProductAttrMapper.mapToProductAttrDto(updatedAttr);
	}

	@Override
	public void deleteAttrById(Long productId, Long attrId) {
		Product product = productRepository.findById(productId)
				.orElseThrow(() -> new ResourceNotFoundException("product id not found"));
		ProductAttr productAttr = productAttrRepository.findById(attrId)
				.orElseThrow(() -> new ResourceNotFoundException("product attribute id not found"));
		if (product != null) {
			productAttrRepository.delete(productAttr);
		}

	}

}
