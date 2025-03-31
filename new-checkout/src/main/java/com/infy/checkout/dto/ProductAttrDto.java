package com.infy.checkout.dto;

public class ProductAttrDto {
	
    private Long productAttrId;
    private String attributeName;
    private String attributeValue;
    
  
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


	@Override
	public String toString() {
		return "ProductAttrDTO [productAttrId=" + productAttrId + ", attributeName=" + attributeName
				+ ", attributeValue=" + attributeValue + "]";
	}
  
}