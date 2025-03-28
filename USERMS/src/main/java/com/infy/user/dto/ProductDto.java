package com.infy.user.dto;

import java.util.List;


public class ProductDto {

    private Long productId;

    private String name;

    private String description;

    private double price;

    private int stockQuantity;
    private String imgUrl;

    private String sku;

    private Boolean isActive;

  //  private Category category; // To represent the associated category
    private List<ProductAttrDto> attributes; // List of Product Attributes as DTO
	public ProductDto(Long productId, String name, String description, double price, int stockQuantity, String imgUrl,
			String sku, Boolean isActive, List<ProductAttrDto> attributes) {
		super();
		this.productId = productId;
		this.name = name;
		this.description = description;
		this.price = price;
		this.stockQuantity = stockQuantity;
		this.imgUrl = imgUrl;
		this.sku = sku;
		this.isActive = isActive;
	
		this.attributes = attributes;
	}
	public ProductDto(){
		
	}
	public Long getProductId() {
		return productId;
	}
	public void setProductId(Long productId) {
		this.productId = productId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public int getStockQuantity() {
		return stockQuantity;
	}
	public void setStockQuantity(int stockQuantity) {
		this.stockQuantity = stockQuantity;
	}
	public String getImgUrl() {
		return imgUrl;
	}
	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
	public String getSku() {
		return sku;
	}
	public void setSku(String sku) {
		this.sku = sku;
	}
	public Boolean getIsActive() {
		return isActive;
	}
	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}
	
	public List<ProductAttrDto> getAttributes() {
		return attributes;
	}
	public void setAttributes(List<ProductAttrDto> attributes) {
		this.attributes = attributes;
	}

	
}

