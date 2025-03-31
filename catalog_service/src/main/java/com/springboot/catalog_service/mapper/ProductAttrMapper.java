package com.springboot.catalog_service.mapper;

import com.springboot.catalog_service.dto.ProductAttrDto;
import com.springboot.catalog_service.entity.ProductAttr;

public class ProductAttrMapper {

	public static ProductAttr mapToProductAttr(ProductAttrDto productAttrDto) {
		return new ProductAttr(productAttrDto.getProductAttrId(), productAttrDto.getAttributeName(),
				productAttrDto.getAttributeValue(), null);

	}

	public static ProductAttrDto mapToProductAttrDto(ProductAttr productAttr) {
		return new ProductAttrDto(productAttr.getProductAttrId(), productAttr.getAttributeName(),
				productAttr.getAttributeValue(), productAttr.getProduct().getProductId());
	}

}
