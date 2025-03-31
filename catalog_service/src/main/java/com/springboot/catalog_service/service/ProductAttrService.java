package com.springboot.catalog_service.service;

import java.util.List;

import com.springboot.catalog_service.dto.ProductAttrDto;

public interface ProductAttrService {

	ProductAttrDto addNewAttributes(ProductAttrDto productAttrDto, Long productId);

	List<ProductAttrDto> getAllAttributes(Long productId);

	ProductAttrDto getAttributeById(Long productId, Long attrId);

	ProductAttrDto updateAttributeById(ProductAttrDto productAttrDto, Long productId, Long attrId);

	void deleteAttrById(Long productId, Long attrId);

}
