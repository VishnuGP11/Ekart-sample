package com.springboot.catalog_service.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.springboot.catalog_service.dto.CategoryDto;
import com.springboot.catalog_service.entity.Catalog;
import com.springboot.catalog_service.entity.Category;

public class CategoryMapper {

	public static Category mapToCategory(CategoryDto categoryDto) {
		Category category = new Category();
		category.setCategoryId(categoryDto.getCategoryId());
		category.setName(categoryDto.getName());
		category.setDescription(categoryDto.getDescription());
	    {
		    Category parentCategory = new Category();    
			parentCategory.setCategoryId(categoryDto.getParentCategoryId());
			category.setParentCategory(parentCategory);
	    }
		if(categoryDto.getCatalogId() != null) {
			Catalog catalog = new Catalog();
			catalog.setCatalogId(categoryDto.getCatalogId());
			category.setCatalog(catalog);
		}
		if(categoryDto.getChildCategories()!=null) {
		 List<Category> childCategories = new ArrayList<>();
	        for (Long childCategoryId : categoryDto.getChildCategories()) {
	            Category childCategory = new Category();
	            childCategory.setCategoryId(childCategoryId);
	            childCategories.add(childCategory);
	        }
	        category.setChildCategories(childCategories);
		}
		return category;
 	}

	public static CategoryDto mapToCategoryDto(Category category) {
		CategoryDto categoryDto = new CategoryDto();
		categoryDto.setCategoryId(category.getCategoryId());
		categoryDto.setParentCategoryId(
				category.getParentCategory() != null ? category.getParentCategory().getCategoryId() : null);
		categoryDto.setName(category.getName());
		categoryDto.setDescription(category.getDescription());
		categoryDto.setCatalogId(category.getCatalog().getCatalogId());
		categoryDto.setChildCategories(
				category.getChildCategories().stream().map(Category::getCategoryId).collect(Collectors.toList()));
		return categoryDto;
	}
}
