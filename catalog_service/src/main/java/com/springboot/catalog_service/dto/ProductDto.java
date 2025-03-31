package com.springboot.catalog_service.dto;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class ProductDto {

	@Schema(description = "Unique identifier for product")
	private Long productId;
	@NotEmpty(message = "Name must not be empty")
	@Schema(description = "Name of the product")
	private String name;
	@NotEmpty(message = "Description must not be empty")
	@Schema(description = "Description of the product")
	private String description;
	@Min(value = 0, message = "Price must be positive")
	@Schema(description = "price of the product")
	private double price;
	@Min(value = 0, message = "Price must be positive")
	@Schema(description = "stock quantity of the product")
	private int stockQuantity;
	@Schema(description = "image url of the product")
	private String imgUrl;
	@NotEmpty
	@Schema(description = "sku (stock keeping unit)url of the product")
	private String sku;
	@NotNull
	@Schema(description = "to check the product is active or not")
	private Boolean isActive;
	@NotNull
	@Schema(description = "to check the product associated with category")
	private Long categoryId; // To represent the associated category
	@Schema(description = "list of attributes associated with the product attribute")
	private List<ProductAttrDto> attributes; // List of Product Attributes as DTO

	public ProductDto(Long productId, String name, String description, double price, int stockQuantity, String imgUrl,
			String sku, Boolean isActive, Long categoryId, List<ProductAttrDto> attributes) {
		super();
		this.productId = productId;
		this.name = name;
		this.description = description;
		this.price = price;
		this.stockQuantity = stockQuantity;
		this.imgUrl = imgUrl;
		this.sku = sku;
		this.isActive = isActive;
		this.categoryId = categoryId;
		this.attributes = attributes;
	}

	public ProductDto() {

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

	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	public List<ProductAttrDto> getAttributes() {
		return attributes;
	}

	public void setAttributes(List<ProductAttrDto> attributes) {
		this.attributes = attributes;
	}

}
