package com.springboot.catalog_service.repository;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.springboot.catalog_service.entity.Category;
import com.springboot.catalog_service.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

	List<Product> findByCategory(Category category);

	List<Product> findByCategory_CategoryIdAndPriceBetweenAndNameContaining(Long categoryId, Double min_price,
			Double max_price, String name, PageRequest pageRequest);

	List<Product> findByNameContaining(String name, PageRequest pageRequest);

	@Query(nativeQuery = true, value = "select * from products where product_id IN (:productIds)")
	List<Product> findProductByProductIds(@Param("productIds") List<Long> productIds);

}
