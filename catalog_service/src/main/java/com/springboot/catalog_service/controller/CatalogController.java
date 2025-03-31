package com.springboot.catalog_service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.catalog_service.entity.Catalog;
import com.springboot.catalog_service.service.CatalogService;

@RestController
@RequestMapping("/api/catalog")
public class CatalogController {

	@Autowired
	private CatalogService catalogService;

	@PostMapping("/create")
	public ResponseEntity<String> saveCatalog(@RequestBody Catalog catalog) {
		Catalog savedCatalog = catalogService.addCatalog(catalog);
		return new ResponseEntity<>("Catalog is created with Id" + savedCatalog.getCatalogId(), HttpStatus.CREATED);

	}

}
