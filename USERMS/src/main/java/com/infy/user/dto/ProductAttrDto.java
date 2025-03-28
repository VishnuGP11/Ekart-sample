package com.infy.user.dto;


import io.micrometer.common.lang.NonNull;



public class ProductAttrDto {

    private Long productAttrId;
    @NonNull
    private String attributeName;
    @NonNull
    private String attributeValue;
//    @NotNull
//    private Long productId;

  
	public ProductAttrDto(Long productAttrId, String attributeName, String attributeValue) {
		super();
		this.productAttrId = productAttrId;
		this.attributeName = attributeName;
		this.attributeValue = attributeValue;
		//this.productId = productId;
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
//
//	public Long getProductId() {
//		return productId;
//	}
//
//	public void setProductId(Long productId) {
//		this.productId = productId;
//	}
    
    
}
