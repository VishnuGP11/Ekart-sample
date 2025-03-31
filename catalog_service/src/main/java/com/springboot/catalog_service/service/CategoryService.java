package com.springboot.catalog_service.service;

import java.util.List;

import com.springboot.catalog_service.dto.CategoryDto;

public interface CategoryService {

	Long createNewCategory(CategoryDto category);

	CategoryDto getCategoryById(Long category_id);

	List<CategoryDto> getAllCategory();

	CategoryDto updateCategoryById(CategoryDto categoryDto, Long category_id);

	void deleteCategoryById(Long category_id);

}
