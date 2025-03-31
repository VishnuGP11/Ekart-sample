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

import com.springboot.catalog_service.dto.ProductAttrDto;
import com.springboot.catalog_service.service.ProductAttrService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "Catalog service- product attribute Rest Apis", description = "Product Attribute Controller to manage the productAttribute Rest Apis")

@RestController
@RequestMapping("/api/productAttribute")
public class ProductAttrController {

	@Autowired
	private ProductAttrService productAttrService;

	@Operation(summary = "Add product attribute Rest Api", description = "This api is used for to create a product attibute")

	@ApiResponses(value = { @ApiResponse(responseCode = "201", description = "Add product attribute successfully"),
			@ApiResponse(responseCode = "400", description = "invalid  category date"),
			@ApiResponse(responseCode = "500", description = "Internal Server Error") })

	@PostMapping("/{id}/attributes/add")
	public ResponseEntity<ProductAttrDto> addAttributes(@Valid @RequestBody ProductAttrDto productAttrDto,
			BindingResult bindingResult, @PathVariable("id") Long productId) throws MethodArgumentNotValidException {
		if (bindingResult.hasErrors()) {
			throw new MethodArgumentNotValidException(null, bindingResult);
		}
		ProductAttrDto prAttr = productAttrService.addNewAttributes(productAttrDto, productId);
		return new ResponseEntity<>(prAttr, HttpStatus.CREATED);

	}

	@Operation(summary = "Fetch attribute by id Rest Api", description = "This api is used for to fetch an attribute by given id")

	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Fetch category successfully"),
			@ApiResponse(responseCode = "404", description = "category Data not found"),
			@ApiResponse(responseCode = "500", description = "Internal Server Error") })

	@GetMapping("/{id}/attributes/{attrId}")
	public ResponseEntity<ProductAttrDto> getAttribute(@PathVariable("id") Long productId,
			@PathVariable("attrId") Long attrId) {
		ProductAttrDto productAttr = productAttrService.getAttributeById(productId, attrId);
		return ResponseEntity.ok(productAttr);
	}

	@Operation(summary = "Fetch all attribute Rest Api", description = "This api is used for to fetch all attribute")

	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Fetch all attibute  successfully"),
			@ApiResponse(responseCode = "404", description = "category Data not found"),
			@ApiResponse(responseCode = "500", description = "Internal Server Error") })

	@GetMapping("/{id}/attributes")
	public ResponseEntity<List<ProductAttrDto>> getAllAttributes(@PathVariable("id") Long productId) {
		List<ProductAttrDto> attributes = productAttrService.getAllAttributes(productId);
		return ResponseEntity.ok(attributes);
	}

	@Operation(summary = "Update the attribute", description = "This api is used to update the attribute")

	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Fetch category successfully"),
			@ApiResponse(responseCode = "404", description = "category Data not found"),
			@ApiResponse(responseCode = "500", description = "Internal Server Error") })

	@PutMapping("/{id}/attributes/{attrId}")
	public ResponseEntity<ProductAttrDto> updateAttribute(@Valid @RequestBody ProductAttrDto productAttrDto,
			BindingResult bindingResult, @PathVariable("id") Long productId, @PathVariable("attrId") Long attrId)
			throws MethodArgumentNotValidException {
		if (bindingResult.hasErrors()) {
			throw new MethodArgumentNotValidException(null, bindingResult);
		}
		ProductAttrDto prodAttr = productAttrService.updateAttributeById(productAttrDto, productId, attrId);
		return ResponseEntity.ok(prodAttr);
	}

	@Operation(summary = "delete the attribute", description = "This api is used to delete the attribute")

	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "delete attribute successfully"),
			@ApiResponse(responseCode = "404", description = "attribute Data not found"),
			@ApiResponse(responseCode = "500", description = "Internal Server Error") })

	@DeleteMapping("/{id}/attributes/{attrId}")
	public ResponseEntity<String> deleteAttribute(@PathVariable("id") Long productId,
			@PathVariable("attrId") Long attrId) {
		productAttrService.deleteAttrById(productId, attrId);
		return ResponseEntity.ok("Attribute deleted successfully !!!!");
	}

}
