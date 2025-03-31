package com.infy.checkout.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.infy.checkout.entity.CartItem;
import com.infy.checkout.entity.Order;
import com.infy.checkout.exception.InvalidInputException;
import com.infy.checkout.exception.OrderNotFoundException;
import com.infy.checkout.service.OrderService;


@RestController
@CrossOrigin(origins = "*") 
@RequestMapping("/api/orders")
public class OrderController {

	Logger log = LoggerFactory.getLogger(OrderController.class);

	private final OrderService orderService;

	public OrderController(OrderService orderService) {
		this.orderService = orderService;
	}

	// Create a new order
	@PostMapping("/create")
	public ResponseEntity<Order> createOrder(@RequestBody Order orderRequest) throws Exception {
		try {
			
			Order order = orderService.createOrder(orderRequest);
			log.info("order details: " + order);
			return new ResponseEntity<>(order, HttpStatus.CREATED);
		} catch (InvalidInputException e) {
			log.error("Invalid input: {}", e.getMessage());
			throw e;
		}
	}
	@PostMapping("/placeOrder/{orderId}")
	public ResponseEntity<Order> addCart(@RequestBody List<CartItem> cartItems, @PathVariable Integer orderId) throws Exception {
		try {
			
			Order order = orderService.placeOrder(cartItems,orderId);
			log.info("order details: " + order);
			return new ResponseEntity<>(order, HttpStatus.OK);
		} catch (InvalidInputException e) {
			log.error("Invalid input: {}", e.getMessage());
			throw e;
		}
	}

	// Edit an existing order (update total amount and status)
	@PutMapping("/update/{orderId}")
	public ResponseEntity<Order> updateOrder(@RequestBody Order orderRequest, @PathVariable Integer orderId)
			throws Exception {
		try {
			Order order = orderService.updateOrder(orderRequest,orderId);
			log.info("the updated order details: {} " + order);
			return new ResponseEntity<Order>(order, HttpStatus.OK);
		} catch (OrderNotFoundException e) {
			log.error("Order not found: {}", e.getMessage());
			throw e;
		} catch (InvalidInputException e) {
			log.error("Invalid input: {}", e.getMessage());
			throw e;
		}
	}

	@GetMapping("/view/{orderId}")
	public ResponseEntity<Order> viewOrder(@PathVariable Integer orderId) throws Exception {
		try {
			Order order = orderService.viewOrder(orderId);
			if (order == null) {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
			log.info("Order details: " + order);
			return new ResponseEntity<>(order, HttpStatus.OK);
		} catch (OrderNotFoundException e) {
			log.error("Order not found: {}", e.getMessage());
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	// Cancel an order by ID
	@PutMapping("/cancel/{orderId}")
	public ResponseEntity<Order> cancelOrder(@PathVariable Integer orderId) throws InterruptedException {
		try {
			Thread.sleep(5000);
			Order cancelledOrder = orderService.cancelOrder(orderId);
			if (cancelledOrder == null) {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
			log.info("Order cancelled: " + cancelledOrder);
			return new ResponseEntity<>(cancelledOrder, HttpStatus.OK);
		} catch (OrderNotFoundException e) {
			log.error("Order not found: {}", e.getMessage());
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

}
