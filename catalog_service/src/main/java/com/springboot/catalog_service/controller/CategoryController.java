package com.springboot.catalog_service.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.catalog_service.dto.CategoryDto;
import com.springboot.catalog_service.service.CategoryService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "Catalog service- Category Rest Apis", description = "Category Controller to manage the Category Rest Apis")

@RestController
@RequestMapping("/api/category")
public class CategoryController {

	@Autowired
	private CategoryService categoryService;

	@Operation(summary = "Add Category Rest Api", description = "This api is used for to create a category")

	@ApiResponses(value = { @ApiResponse(responseCode = "201", description = "Add category successfully"),
			@ApiResponse(responseCode = "400", description = "invalid  category date"),
			@ApiResponse(responseCode = "500", description = "Internal Server Error") })

	@PostMapping("/create")
	public ResponseEntity<String> createCategory(@Valid @RequestBody CategoryDto categoryDto,
			BindingResult bindingResult) throws MethodArgumentNotValidException {
		if (bindingResult.hasErrors()) {
			throw new MethodArgumentNotValidException(null, bindingResult);
		}
		Long categoryId = categoryService.createNewCategory(categoryDto);
		return new ResponseEntity<>("Category created with id : " + categoryId, HttpStatus.CREATED);
	}

	@Operation(summary = "Fetcg Category by id Rest Api", description = "This api is used for to fetch a category by given id")

	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Fetch category successfully"),
			@ApiResponse(responseCode = "404", description = "category Data not found"),
			@ApiResponse(responseCode = "500", description = "Internal Server Error") })

	@GetMapping("{id}")
	public ResponseEntity<CategoryDto> getCategoryById(@PathVariable("id") Long category_id) {
		CategoryDto result = categoryService.getCategoryById(category_id);
		return ResponseEntity.ok(result);
	}

	@Operation(summary = "Fetch all the Category", description = "This api is used for to fetch all the  category")

	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Fetch category successfully"),
			@ApiResponse(responseCode = "404", description = "category Data not found"),
			@ApiResponse(responseCode = "500", description = "Internal Server Error") })
	@GetMapping
	public ResponseEntity<List<CategoryDto>> getAllCategory() {
		List<CategoryDto> listCategories = categoryService.getAllCategory();
		return ResponseEntity.ok(listCategories);
	}

	@Operation(summary = "update the Category", description = "This api is used for to update category")

	@ApiResponses(value = { @ApiResponse(responseCode = "201", description = "Fetch category successfully"),
			@ApiResponse(responseCode = "404", description = "category Data not found"),
			@ApiResponse(responseCode = "500", description = "Internal Server Error") })

	@PutMapping("{id}")
	public ResponseEntity<CategoryDto> updateCategory(@RequestBody CategoryDto categoryDto,
			@PathVariable("id") Long category_id)   {
		CategoryDto updatedCategory = categoryService.updateCategoryById(categoryDto, category_id);
		return ResponseEntity.ok(updatedCategory);
	}

	@Operation(summary = "delete Category", description = "This api is used to delete category")

	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Delete category successfully"),
			@ApiResponse(responseCode = "404", description = "category Data not found"),
			@ApiResponse(responseCode = "500", description = "Internal Server Error") })

	@DeleteMapping("{id}")
	public ResponseEntity<String> deleteCategory(@PathVariable("id") Long category_id) {
		categoryService.deleteCategoryById(category_id);
		return ResponseEntity.ok("Category deleted successfully with id!!!!" + category_id);
	}

}
