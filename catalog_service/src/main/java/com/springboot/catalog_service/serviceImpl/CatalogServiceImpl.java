package com.springboot.catalog_service.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.catalog_service.entity.Catalog;
import com.springboot.catalog_service.repository.CatalogRepository;
import com.springboot.catalog_service.service.CatalogService;

@Service
public class CatalogServiceImpl implements CatalogService {

	@Autowired
	private CatalogRepository catalogRepo;

	@Override
	public Catalog addCatalog(Catalog catalog) {
		Catalog newCatalog = catalogRepo.save(catalog);
		return newCatalog;
	}

}
