package com.infy.payment_service.controller;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.infy.payment_service.model.Payment;
import com.infy.payment_service.service.PaymentService;

@RestController
@RequestMapping("/api/paymentservice")
public class PaymentController {

	private static final Logger logger = LoggerFactory.getLogger(PaymentController.class);
	@Autowired
	PaymentService service;

	@PostMapping("/payments")
	public ResponseEntity<Payment> addPayment(@RequestBody Payment e) {
		try {
			logger.info("Adding payment: {}", e);
			Payment payment=service.addPayment(e);
			return new ResponseEntity<>(payment,HttpStatus.CREATED);
		} catch (Exception ex) {
			logger.error("Error adding payment: {}", e, ex);
			throw new RuntimeException("Error adding payment", ex); // or handle accordingly
		}
	}

	@PutMapping("/payments/{paymentId}")
	public String editPayment(@RequestBody Payment e, @PathVariable int paymentId) {
		try {
			logger.info("Editing payment with ID: {} and new data: {}", paymentId, e);
			return service.editPayment(e, paymentId);
		} catch (Exception ex) {
			logger.error("Error editing payment with ID: {}", paymentId, ex);
			throw new RuntimeException("Error editing payment", ex);
		}
	}

	@DeleteMapping("/payments/{paymentId}")
	public String deletePayment(@PathVariable int paymentId) {
		try {
			logger.info("Deleting payment with ID: {} ", paymentId);
			return service.deletePayment(paymentId);
		} catch (Exception ex) {
			logger.error("Error deleting payment with ID: {}", paymentId, ex);
			throw new RuntimeException("Error deleting payment", ex);
		}
	}


	@GetMapping("/getPayment/{paymentId}")
	public ResponseEntity<?> getPayment(@PathVariable int paymentId) {
		try {
			Optional<Payment> payment = service.getPayment(paymentId);
			if (payment.isPresent()) {
				logger.info("Payment found with ID: {}", paymentId);
				return ResponseEntity.ok(payment.get());
			} else {
				logger.warn("Payment not found with ID: {}", paymentId);
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Payment not found with ID: " + paymentId);
			}
		} catch (Exception ex) {
			logger.error("Error fetching payment with ID: {}", paymentId, ex);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Error fetching payment: " + ex.getMessage());
		}
	}

}
