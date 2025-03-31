package com.infy.payment_service.service;

import java.time.LocalDate;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.infy.payment_service.dto.OrderDTO;
import com.infy.payment_service.model.Payment;
import com.infy.payment_service.repository.PaymentRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import jakarta.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class PaymentService {

	private static final Logger logger = LoggerFactory.getLogger(PaymentService.class);

	private final RestTemplate restTemplate;
	private PaymentRepository paymentRepository;
	@Value("${orderservice.api.url}")
	private String OrderServiceAPI;

	public PaymentService(RestTemplate restTemplate, PaymentRepository paymentRepository) {
		this.restTemplate = restTemplate;
		this.paymentRepository = paymentRepository;
	}

	public OrderDTO getOrderDetails(int orderId) throws Exception {
		try {
			// append the userId or the required details in the API endpoint
			OrderDTO orderDetails = restTemplate.getForObject(OrderServiceAPI, OrderDTO.class, orderId);
			return orderDetails;
		} catch (Exception e) {
			// Log the exception and rethrow it so that the circuit breaker can handle it
			logger.error("Error fetching user details for userId: {}. Exception: {}", orderId, e.getMessage(), e);
			throw new RuntimeException("Failed to fetch user details from the User Service.", e);
		}
	}

	// Add a new payment
	@Transactional
	@CircuitBreaker(name = "{spring.application.name}", fallbackMethod = "fallbackPaymentDetails")
	public Payment addPayment(Payment payment) throws Exception {
		try {
			// Save the payment to the database
			OrderDTO order = getOrderDetails(payment.getOrderId());

			if (order.getOrderId() == null && order.getUserId() == null) {
				throw new RuntimeException("User with ID: " + payment.getUser_id() + " and OrderID "
						+ payment.getOrderId() + " does not exist.");
			}
			Payment addedPayment = paymentRepository.save(payment);
			logger.info("Payment added successfully with ID: {}", payment.getPaymentId());
			return addedPayment;
		} catch (Exception e) {
			logger.error("Error occurred while adding payment: {}", e.getMessage());
			throw new RuntimeException("Error occurred while adding payment. Please try again later.");
		}
	}

	public Payment fallbackPaymentDetails(Payment payment, Throwable throwable) {
		logger.error("Error from fallback: {} Error while fetching order details from CheckoutMS: {}: ",
				throwable.getMessage());

		// Return a default or empty OrderDTO to avoid breaking the flow
		Payment defaultPayment = new Payment();
		defaultPayment.setCardNumber("00000000000");
		defaultPayment.setCvv("000");
		defaultPayment.setExpiryDate(LocalDate.now());
		defaultPayment.setPaymentStatus("Unavailable");

		return defaultPayment;
	}

	// Edit an existing payment
	public String editPayment(Payment payment, int paymentId) {
		try {
			Optional<Payment> existingPayment = paymentRepository.findById(paymentId);

			if (existingPayment.isPresent()) {
				// Update the payment with the new details
				Payment updatedPayment = existingPayment.get();
				updatedPayment.setCardNumber(payment.getCardNumber());
				updatedPayment.setCvv(payment.getCvv());
				updatedPayment.setExpiryDate(payment.getExpiryDate());
				updatedPayment.setPaymentStatus(payment.getPaymentStatus());

				// Save the updated payment to the database
				paymentRepository.save(updatedPayment);
				logger.info("Payment with ID {} updated successfully.", paymentId);
				return "Payment Updated Successfully.";
			} else {
				logger.warn("Payment not found with ID: {}", paymentId);
				return "Payment not found with ID: " + paymentId;
			}
		} catch (Exception e) {
			logger.error("Error occurred while editing payment with ID {}: {}", paymentId, e.getMessage());
			return "Error occurred while editing payment. Please try again later.";
		}
	}

	// Delete a payment by ID
	public String deletePayment(int paymentId) {
		try {
			Optional<Payment> existingPayment = paymentRepository.findById(paymentId);

			if (existingPayment.isPresent()) {
				// Delete the payment from the database
				paymentRepository.deleteById(paymentId);
				logger.info("Payment with ID {} deleted successfully.", paymentId);
				return "Payment Deleted Successfully.";
			} else {
				logger.warn("Payment not found with ID: {}", paymentId);
				return "Payment not found with ID: " + paymentId;
			}
		} catch (Exception e) {
			logger.error("Error occurred while deleting payment with ID {}: {}", paymentId, e.getMessage());
			return "Error occurred while deleting payment. Please try again later.";
		}
	}

	public Optional<Payment> getPayment(int paymentId) {
		try {
			logger.info("Attempting to fetch payment with ID: {}", paymentId);
			Optional<Payment> payment = paymentRepository.findById(paymentId);
			if (payment.isPresent()) {
				logger.info("Payment found with ID: {}", paymentId);
			} else {
				logger.warn("No payment found with ID: {}", paymentId);
			}
			return payment;
		} catch (Exception ex) {
			logger.error("Error occurred while fetching payment with ID: {}", paymentId, ex);
			throw new RuntimeException("Error fetching payment", ex); // Throw a runtime exception or a custom exception
																		// as per your need
		}
	}
}
