package com.springboot.catalog_service.entity;

import java.sql.Timestamp;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "products_attr")
public class ProductAttr {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long productAttrId;
	@Column(nullable = false)
	private String attributeName;
	@Column(nullable = false)
	private String attributeValue;
	@CreationTimestamp
	private Timestamp createdAt;
	@UpdateTimestamp
	private Timestamp updatedAt;
	@ManyToOne
	@JoinColumn(name = "product_id", nullable = false)
	@JsonManagedReference
	private Product product;
	
	
	public ProductAttr(Long productAttrId, String attributeName, String attributeValue, Timestamp createdAt,
			Timestamp updatedAt, Product product) {
		super();
		this.productAttrId = productAttrId;
		this.attributeName = attributeName;
		this.attributeValue = attributeValue;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.product = product;
	}
	public ProductAttr() {
		
	}
	public ProductAttr(Long productAttrId2, String attributeName2, String attributeValue2, Long productId) {
		this.productAttrId = productAttrId2;
		this.attributeName = attributeName2;
		this.attributeValue = attributeValue2;
	}
	
	
	public Long getProductAttrId() {
		return productAttrId;
	}
	public void setProductAttrId(Long productAttrId) {
		this.productAttrId = productAttrId;
	}
	public String getAttributeName() {
		return attributeName;
	}
	public void setAttributeName(String attributeName) {
		this.attributeName = attributeName;
	}
	public String getAttributeValue() {
		return attributeValue;
	}
	public void setAttributeValue(String attributeValue) {
		this.attributeValue = attributeValue;
	}
	public Timestamp getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}
	public Timestamp getUpdatedAt() {
		return updatedAt;
	}
	public void setUpdatedAt(Timestamp updatedAt) {
		this.updatedAt = updatedAt;
	}
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public Long getProductId(Product product) {
		return product.getProductId();
	}
	
	
	
	
}
