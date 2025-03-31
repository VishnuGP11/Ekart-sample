package com.infy.payment_service.model;

import java.time.LocalDate;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "Payment")
public class Payment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "payment_id")
	private int paymentId;

	// Add the customerId as a foreign key reference to the Customer entity
	@Column(name = "user_id", nullable = false)
	private Long user_id;

	@Column(name = "order_id", nullable = false)
	private int orderId;

	@Column(name = "card_number", nullable = false)
	private String cardNumber;

	@Column(name = "cvv", nullable = false)
	private String cvv;

	@Column(name = "expiry_date", nullable = false)
	private LocalDate expiryDate;

	private static String paymentMethod = "Credit Card"; // Static field, no need for persistence
	private String paymentStatus;

	// Getter and Setter methods

	public int getPaymentId() {
		return paymentId;
	}

	public void setPaymentId(int paymentId) {
		this.paymentId = paymentId;
	}

	public Long getUser_id() {
		return user_id;
	}

	public void setUser_id(Long user_id) {
		this.user_id = user_id;
	}

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public String getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	public String getCvv() {
		return cvv;
	}

	public void setCvv(String cvv) {
		this.cvv = cvv;
	}

	public LocalDate getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(LocalDate expiryDate) {
		this.expiryDate = expiryDate;
	}

	public static String getPaymentMethod() {
		return paymentMethod;
	}

	public static void setPaymentMethod(String paymentMethod) {
		Payment.paymentMethod = paymentMethod;
	}

	public String getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}
}
