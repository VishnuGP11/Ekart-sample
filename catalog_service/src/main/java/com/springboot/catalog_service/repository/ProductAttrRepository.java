package com.springboot.catalog_service.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.springboot.catalog_service.entity.ProductAttr;

@Repository
public interface ProductAttrRepository extends JpaRepository<ProductAttr, Long>{
	
	   List<ProductAttr> findByProduct_ProductId(Long productId);

}
