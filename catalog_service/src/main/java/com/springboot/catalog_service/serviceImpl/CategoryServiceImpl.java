package com.springboot.catalog_service.serviceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.catalog_service.dto.CategoryDto;
import com.springboot.catalog_service.entity.Catalog;
import com.springboot.catalog_service.entity.Category;
import com.springboot.catalog_service.exception.ResourceNotFoundException;
import com.springboot.catalog_service.mapper.CategoryMapper;
import com.springboot.catalog_service.repository.CatalogRepository;
import com.springboot.catalog_service.repository.CategoryRepository;
import com.springboot.catalog_service.service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private CatalogRepository catalogRepository;

	@Autowired
	private CategoryRepository categoryRepository;

	@Override
	public Long createNewCategory(CategoryDto categoryDto) {
		Catalog catalog = catalogRepository.findById(categoryDto.getCatalogId())
				.orElseThrow(() -> new ResourceNotFoundException("catalog not found "));
		Category category = CategoryMapper.mapToCategory(categoryDto);
		category.setCatalog(catalog);
		if (category.getParentCategory().getCategoryId() != null) {
			Category parentCat = categoryRepository.findById(category.getParentCategory().getCategoryId())
					.orElseThrow(() -> new ResourceNotFoundException("Parent category not found"));
			category.setParentCategory(parentCat);
		} else {
			category.setParentCategory(null);
		}
		Category savedCategory = categoryRepository.save(category);

		return savedCategory.getCategoryId();
	}

	@Override
	public CategoryDto getCategoryById(Long category_id) {
		try {
			if (category_id == null) {
				return null;
			}
			Category category = categoryRepository.findById(category_id)
					.orElseThrow(() -> new ResourceNotFoundException("Category not found "));

			return CategoryMapper.mapToCategoryDto(category);
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public List<CategoryDto> getAllCategory() {
		List<Category> allCategories = categoryRepository.findAll();
		return allCategories.stream().map(category -> CategoryMapper.mapToCategoryDto(category))
				.collect(Collectors.toList());
	}

	@Override
	public CategoryDto updateCategoryById(CategoryDto categoryDto, Long category_id) {
		try {
			if (categoryDto == null || category_id == null) {
				return categoryDto;
			}
			Category existingCategory = categoryRepository.findById(category_id)
					.orElseThrow(() -> new ResourceNotFoundException("Category not found "));
			Category category = CategoryMapper.mapToCategory(categoryDto);
			if (category.getName() != null) {
				existingCategory.setName(category.getName());
			}

			if (category.getDescription() != null) {
				existingCategory.setDescription(category.getDescription());
			}

			if (category.getParentCategory() != null && category.getParentCategory().getCategoryId() != null) {
				Category parentCat = categoryRepository.findById(category.getParentCategory().getCategoryId())
						.orElseThrow(() -> new ResourceNotFoundException("Parent category not found"));
				existingCategory.setParentCategory(parentCat);
			} else {
				existingCategory.setParentCategory(null);
			}

			if (category.getCatalog() != null && category.getCatalog().getCatalogId() != null) {
				Catalog catalog = catalogRepository.findById(category.getCatalog().getCatalogId())
						.orElseThrow(() -> new ResourceNotFoundException("Catalog not found"));
				existingCategory.setCatalog(catalog);
			}
			if (category.getChildCategories() != null) {
				List<Category> childCategories = new ArrayList<>();
				for (Long childCategoryId : categoryDto.getChildCategories()) {
					Category childCategory = new Category();
					childCategory.setCategoryId(childCategoryId);
					childCategories.add(childCategory);
				}
				existingCategory.setChildCategories(childCategories);
			}
			Category updatedCategory = categoryRepository.save(existingCategory);
			return CategoryMapper.mapToCategoryDto(updatedCategory);
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public void deleteCategoryById(Long category_id) {
		Category category = categoryRepository.findById(category_id)
				.orElseThrow(() -> new RuntimeException("category not found "));
		categoryRepository.delete(category);
	}

}
