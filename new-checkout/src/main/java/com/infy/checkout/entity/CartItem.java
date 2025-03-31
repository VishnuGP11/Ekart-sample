package com.infy.checkout.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.infy.checkout.dto.ProductDto;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;


@Entity
@Table(name="CartItem")
public class CartItem {
	
	@Id
	private Long cartId;
	
	@ManyToOne
	@JoinColumn(name="order_id", nullable=false)
	@JsonBackReference
	private Order order;
	
	
	private Long productId;
	
	
	private Integer quantity;
	
	
	
	@Transient
	private ProductDto productDTO;
	

	
	public ProductDto getProductDTO() {
		return productDTO;
	}

	public void setProductDTO(ProductDto productDTO) {
		this.productDTO = productDTO;
	}

	public Long getCartId() {
		return cartId;
	}

	public void setCartId(Long cartId) {
		this.cartId = cartId;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	
	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}


	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	@Override
	public String toString() {
		return "CartItem [cartId=" + cartId + ", productId=" + productId + ", quantity=" + quantity
				+  "]";
	}
	

	

	

	
	
	
	
	
}
	
	
	 