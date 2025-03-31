package com.springboot.catalog_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.springboot.catalog_service.entity.Catalog;

@Repository
public interface CatalogRepository extends JpaRepository<Catalog, Long> {

}
