package com.springboot.catalog_service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class ProductAttrDto {

	@Schema(description = "unique identifier for the product id")
	private Long productAttrId;
	@NotEmpty
	@Schema(description = "attribute name for the product")
	private String attributeName;
	@NotEmpty
	@Schema(description = "attribute value for the product")
	private String attributeValue;
	@NotNull
	@Schema(description = "product id associated with product attribute")
	private Long productId;

	public ProductAttrDto(Long productAttrId, String attributeName, String attributeValue, Long productId) {
		super();
		this.productAttrId = productAttrId;
		this.attributeName = attributeName;
		this.attributeValue = attributeValue;
		this.productId = productId;
	}

	public ProductAttrDto() {

	}

	// Getters and Setters
	public Long getProductAttrId() {
		return productAttrId;
	}

	public void setProductAttrId(Long productAttrId) {
		this.productAttrId = productAttrId;
	}

	public String getAttributeName() {
		return attributeName;
	}

	public void setAttributeName(String attributeName) {
		this.attributeName = attributeName;
	}

	public String getAttributeValue() {
		return attributeValue;
	}

	public void setAttributeValue(String attributeValue) {
		this.attributeValue = attributeValue;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

}
