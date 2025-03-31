package com.springboot.catalog_service.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.catalog_service.dto.ProductDto;
import com.springboot.catalog_service.service.ProductService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "Catalog service- product Rest Apis", description = "Product Controller to manage the product Rest Apis")

@RestController
@RequestMapping("/api/products")
public class ProductController {

	@Autowired
	private ProductService productService;

	@Operation(summary = "Add product Rest Api", description = "This api is used for to create a product")

	@ApiResponses(value = { @ApiResponse(responseCode = "201", description = "Add product successfully"),
			@ApiResponse(responseCode = "400", description = "invalid  product data"),
			@ApiResponse(responseCode = "500", description = "Internal Server Error") })

	@PostMapping("/add")
	public ResponseEntity<String> addProduct(@Valid @RequestBody ProductDto productDto, BindingResult bindingResult)
			throws MethodArgumentNotValidException {
		if (bindingResult.hasErrors()) {
			throw new MethodArgumentNotValidException(null, bindingResult);
		}
		Long productId = productService.addNewProduct(productDto);
		return new ResponseEntity<>("Id created with :" + productId, HttpStatus.CREATED);
	}

	@Operation(summary = "Fetch product by id Rest Api", description = "This api is used for to fetch an product by given id")

	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Fetch category successfully"),
			@ApiResponse(responseCode = "404", description = "product Data not found"),
			@ApiResponse(responseCode = "500", description = "Internal Server Error") })

	@GetMapping("{id}")
	public ResponseEntity<ProductDto> getProduct(@PathVariable("id") Long product_id) {
		ProductDto productDto = productService.getProductById(product_id);
		return ResponseEntity.ok(productDto);
	}

	@Operation(summary = "Fetch all product Rest Api", description = "This api is used for to fetch all product")

	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Fetch all product  successfully"),
			@ApiResponse(responseCode = "404", description = "Product Data not found"),
			@ApiResponse(responseCode = "500", description = "Internal Server Error") })

	@GetMapping
	public ResponseEntity<List<ProductDto>> getAllProducts() {
		List<ProductDto> allProducts = productService.getAllProducts();
		return ResponseEntity.ok(allProducts);
	}

	@Operation(summary = "Update the product", description = "This api is used to update the product")

	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "updated product  successfully"),
			@ApiResponse(responseCode = "404", description = "product Data not found"),
			@ApiResponse(responseCode = "500", description = "Internal Server Error") })

	@PutMapping("{id}")
	public ResponseEntity<ProductDto> updateProduct(@Valid @RequestBody ProductDto productDto,
			BindingResult bindingResult, @PathVariable("id") Long product_id) throws MethodArgumentNotValidException {
		if (bindingResult.hasErrors()) {
			throw new MethodArgumentNotValidException(null, bindingResult);
		}
		ProductDto updatedProduct = productService.updateProductsById(productDto, product_id);
		return ResponseEntity.ok(updatedProduct);
	}

	@Operation(summary = "delete the product", description = "This api is used to delete the product")

	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "delete product successfully"),
			@ApiResponse(responseCode = "404", description = "Product Data not found"),
			@ApiResponse(responseCode = "500", description = "Internal Server Error") })

	@DeleteMapping("{id}")
	public ResponseEntity<String> deleteProduct(@PathVariable("id") Long product_id) {
		productService.deleteProductById(product_id);
		return ResponseEntity.ok("Product deleted successfully !!!!");
	}

	@Operation(summary = "get the product by category id", description = "This api is used to get the product")

	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "fetch product successfully"),
			@ApiResponse(responseCode = "404", description = "category not found"),
			@ApiResponse(responseCode = "500", description = "Internal Server Error") })

	@GetMapping("category/{id}")
	public ResponseEntity<List<ProductDto>> getProductsByCategory(@PathVariable("id") Long category_id) {
		List<ProductDto> products = productService.getAllProductsByCategory(category_id);
		return ResponseEntity.ok(products);
	}

	@Operation(summary = "serch the product", description = "This api is used to search  the product")

	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "fetch product successfully"),
			@ApiResponse(responseCode = "404", description = "product not found"),
			@ApiResponse(responseCode = "500", description = "Internal Server Error") })

	@GetMapping("/filterBy")
	public ResponseEntity<List<ProductDto>> productsFilter(@RequestParam(required = false) Long categoryId,
			@RequestParam(required = false) Double min_price, @RequestParam(required = false) Double max_price,
			@RequestParam(required = false) String name, @RequestParam(required = false) String sort,
			@RequestParam(required = false) String direction) {
		System.out.println(categoryId);
		System.out.println(min_price);
		System.out.println(max_price);
		System.out.println(name);
		Sort.Direction sortDirection = (direction == null || direction.equalsIgnoreCase("asc")) ? Sort.Direction.ASC
				: Sort.Direction.DESC;
		PageRequest pageRequest = PageRequest.of(0, 100, Sort.by(sortDirection, sort != null ? sort : "name"));
		List<ProductDto> products = productService.productsFilter(categoryId, min_price, max_price, name, pageRequest);
		return ResponseEntity.ok(products);
	}

	@Operation(summary = "sort the product", description = "This api is used to sort the product")

	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "sort product successfully"),
			@ApiResponse(responseCode = "404", description = "product not found"),
			@ApiResponse(responseCode = "500", description = "Internal Server Error") })

	@GetMapping("/sortBy")
	public ResponseEntity<List<ProductDto>> productsFilter(@RequestParam(required = false) String name,
			@RequestParam(required = false) String sort, @RequestParam(required = false) String direction) {

		System.out.println(name);
		Sort.Direction sortDirection = (direction == null || direction.equalsIgnoreCase("asc")) ? Sort.Direction.ASC
				: Sort.Direction.DESC;
		PageRequest pageRequest = PageRequest.of(0, 100, Sort.by(sortDirection, sort != null ? sort : "name"));
		List<ProductDto> products = productService.productsSort(name, pageRequest);
		return ResponseEntity.ok(products);

	}

	@Operation(summary = "get all product by category id", description = "This api is used to get all product")

	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "fetch product successfully"),
			@ApiResponse(responseCode = "404", description = "category not found"),
			@ApiResponse(responseCode = "500", description = "Internal Server Error") })

	@PostMapping("/by-ids")
	public ResponseEntity<List<ProductDto>> getProductByIds(@RequestBody List<Long> productIds) {
		List<ProductDto> products = productService.getProductsByIds(productIds);
		return ResponseEntity.ok(products);
	}
}
