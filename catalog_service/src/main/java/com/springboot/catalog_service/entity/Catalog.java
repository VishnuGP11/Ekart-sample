package com.springboot.catalog_service.entity;

import java.sql.Timestamp;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "catalogs")
public class Catalog {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long catalogId;
	private String name;
	private String description;
	private Boolean isActive = true;
	@CreationTimestamp
	private Timestamp createdAt;
	@UpdateTimestamp
	private Timestamp updatedAt;

	public Long getCatalogId() {
		return catalogId;
	}

	public void setCatalogId(Long catalogId) {
		this.catalogId = catalogId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
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

//	 @PrePersist
//	    protected void onCreate() {
//	        if (this.createdAt == null) {
//	            this.createdAt = new Timestamp(System.currentTimeMillis());
//	        }
//	        if (this.updatedAt == null) {
//	            this.updatedAt = new Timestamp(System.currentTimeMillis());
//	        }
//	    }

//	    @PreUpdate
//	    protected void onUpdate() {
//	        this.updatedAt = new Timestamp(System.currentTimeMillis());
//	    }

}
