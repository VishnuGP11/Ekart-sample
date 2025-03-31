package com.infy.dto;

import java.util.Date;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;


@Entity
@Table(name="CartIteam")
public class CartIteamDto {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long cartId;
	
	private Long userId;
	
	private Long productId;
	
	private Date createdDate;
	
	private Integer quantity;
	
	private Double price;
	
	private Double totalPrice;
	
	 @Column(name = "lst_updt_date")
	private Date LstUpdtDate;
	
	private Integer IsOrderPlaced;
	
	private Integer isActive;
	
	@Transient
	private ProductDto productDTO;

	public Long getCartId() {
		return cartId;
	}

	public void setCartId(Long cartId) {
		this.cartId = cartId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public Date getLstUpdtDate() {
		return LstUpdtDate;
	}
	

	public void setLstUpdtDate(Date LstUpdtDate) {
		this.LstUpdtDate = LstUpdtDate;
	}

	public Integer getIsOrderPlaced() {
		return IsOrderPlaced;
	}

	public void setIsOrderPlaced(Integer isOrderPlaced) {
		IsOrderPlaced = isOrderPlaced;
	}

	public Integer getIsActive() {
		return isActive;
	}

	public void setIsActive(Integer isActive) {
		this.isActive = isActive;
	}

	public ProductDto getProductDTO() {
		return productDTO;
	}

	public void setProductDTO(ProductDto productDTO) {
		this.productDTO = productDTO;
	}

	@Override
	public String toString() {
		return "CartDTO [cartId=" + cartId + ", userId=" + userId + ", productId=" + productId + ", createdDate="
				+ createdDate + ", quantity=" + quantity + ", price=" + price + ", totalPrice=" + totalPrice
				+ ", LstUpdtDate=" + LstUpdtDate + ", IsOrderPlaced=" + IsOrderPlaced + ", isActive=" + isActive
				+ ", productDTO=" + productDTO + "]";
	}
	
	
	
}
	
	
	 