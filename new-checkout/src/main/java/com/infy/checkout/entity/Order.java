package com.infy.checkout.entity;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.infy.checkout.dto.AddressDTO;
import com.infy.checkout.dto.PaymentDTO;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity
@Table(name = "new_orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer orderId;

    private Long user_id; // The user who placed this order

    @Column(name = "total_amount")
    private Double totalAmount;

    @Column(name = "status")
    private String status;//pending processing shipped completed placed
    
    @Column(name = "shipAddressId")
    private Long shipAddressId;
    
    @Column(name = "paymentId")
    private Long paymentId;
    
    @Column(name = "tax")
    private Double tax;
    
    @Column(name = "taxId")
    private Long taxId;
    
    @OneToMany(mappedBy="order", cascade= CascadeType.ALL)
    @JsonManagedReference
    private List<CartItem> cartItem;
    
    @Transient
    private AddressDTO address;
    
    @Transient
    private PaymentDTO payment;

   
	public PaymentDTO getPayment() {
		return payment;
	}

	public void setPayment(PaymentDTO payment) {
		this.payment = payment;
	}

	public AddressDTO getAddress() {
		return address;
	}

	public void setAddress(AddressDTO address) {
		this.address = address;
	}

	@Column(name = "created_at",updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // Getters and Setters
    public Integer getOrderId() {
        return orderId;
    }

	public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

	public Long getUser_id() {
		return user_id;
	}

	public void setUser_id(Long user_id) {
		this.user_id = user_id;
	}

	public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
    public List<CartItem> getCartItem() {
		return cartItem;
	}

	public void setCartItem(List<CartItem> cartItem) {
		this.cartItem = cartItem;
	}

	@Override
	public String toString() {
		return "Order [orderId=" + orderId + ", user_id=" + user_id + ", totalAmount=" + totalAmount + ", status="
				+ status + ", shipAddressId=" + shipAddressId + ", paymentId=" + paymentId + ", tax=" + tax + ", taxId="
				+ taxId + ", cartItem=" + cartItem + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + "]";
	}

	public Long getShipAddressId() {
		return shipAddressId;
	}

	public void setShipAddressId(Long shipAddressId) {
		this.shipAddressId = shipAddressId;
	}

	public Long getPaymentId() {
		return paymentId;
	}

	public void setPaymentId(Long paymentId) {
		this.paymentId = paymentId;
	}

	public Double getTax() {
		return tax;
	}

	public void setTax(Double tax) {
		this.tax = tax;
	}

	public Long getTaxId() {
		return taxId;
	}

	public void setTaxId(Long taxId) {
		this.taxId = taxId;
	}

	    
}