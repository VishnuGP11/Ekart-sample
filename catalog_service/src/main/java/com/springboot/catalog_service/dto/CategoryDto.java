package com.springboot.catalog_service.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;

public class CategoryDto {

	@Schema(description = "Unique identifier of the category")
	private Long categoryId;
	@NotEmpty(message = "name must not be null")
	@Schema(description = "name of the category")
	private String name;
	@NotEmpty(message = "description must not be null")
	@Schema(description = "description of the category")
	private String description;
	private Long parentCategoryId;
	@Schema(description = "catalog id assocaited with this category")
	private Long catalogId;
	@Schema(description = "list of child category ids")
	private List<Long> childCategories;

	public CategoryDto() {

	}

	public CategoryDto(Long categoryId, String name, String description, Long parentCategoryId, Long catalogId,
			List<Long> childCategories) {
		super();
		this.categoryId = categoryId;
		this.name = name;
		this.description = description;
		this.parentCategoryId = parentCategoryId;
		this.catalogId = catalogId;
		this.childCategories = childCategories;
	}

	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
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

	public Long getParentCategoryId() {
		return parentCategoryId;
	}

	public void setParentCategoryId(Long parentCategoryId) {
		this.parentCategoryId = parentCategoryId;
	}

	public Long getCatalogId() {
		return catalogId;
	}

	public void setCatalogId(Long catalogId) {
		this.catalogId = catalogId;
	}

	public List<Long> getChildCategories() {
		return childCategories;
	}

	public void setChildCategories(List<Long> childCategories) {
		this.childCategories = childCategories;
	}

}
