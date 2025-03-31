package com.infy.checkout.service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.infy.checkout.dto.PaymentDTO;
import com.infy.checkout.dto.ProductDto;
import com.infy.checkout.dto.UserAndAddressDTO;
import com.infy.checkout.dto.UserDTO;
import com.infy.checkout.entity.CartItem;
import com.infy.checkout.entity.Order;
import com.infy.checkout.repository.OrderRepository;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import jakarta.transaction.Transactional;

@Service
public class OrderService {

	private Logger log = LoggerFactory.getLogger(OrderService.class);

	private final OrderRepository orderRepository;
	private final RestTemplate restTemplate;

	@Value("${taxservice.api.url}")
	private String TaxServiceAPI;

	@Value("${userservice.api.url}")
	private String UserServiceAPI;
	
	@Value("${addressservice.api.url}")
	private String UserAddressServiceAPI;
	
	@Value("${paymentservice.api.url}")
	private String PaymentServiceAPI;
	
	@Value("${cartService.api.url}")
	private String CartServiceAPI;


	public OrderService(OrderRepository orderRepository, RestTemplate restTemplate) {
		this.orderRepository = orderRepository;

		this.restTemplate = restTemplate;
	}

	@Transactional
	@CircuitBreaker(name = "{spring.application.name}", fallbackMethod = "fallbackUserDetails")
	public UserDTO getUserDetails(Long userId) throws Exception {
		try {
			// append the userId or the required details in the API endpoint
			UserDTO userdetail = restTemplate.getForObject(UserServiceAPI, UserDTO.class, userId);
			return userdetail;
		} catch (Exception e) {
			// Log the exception and rethrow it so that the circuit breaker can handle it
			log.error("Error fetching user details for userId: {}. Exception: {}", userId, e.getMessage(), e);
			throw new RuntimeException("Failed to fetch user details from the User Service.", e);
		}
	}

	public UserDTO fallbackUserDetails(Long userId, Throwable throwable) {
		log.error("Error fetching user details for userId: {}. Error: {}", userId, throwable.getMessage());

		// Returning a default or empty UserDTO to avoid breaking the flow
		UserDTO defaultUser = new UserDTO();
		defaultUser.setUserId(userId); // Set the userId even if the user details are not found
		defaultUser.setUsername("DefaultUser");

		return defaultUser;
	}

	@Transactional
	@CircuitBreaker(name = "${spring.application.name}", fallbackMethod = "fallbackCreateOrder")
	public Order createOrder(Order orderRequest) throws Exception {
		log.info("Creating order for the details {}", orderRequest);
		Long userId = orderRequest.getUser_id();

		// Fetch user details from UserServiceAPI
		UserDTO user = getUserDetails(userId); // Use the method to fetch user details

		if (user == null || user.getUserId() == null) {
			throw new RuntimeException("User details not found for userId: " + userId);
		}

		Order order = new Order();
		order.setUser_id(userId);
		order.setStatus(orderRequest.getStatus());
		order.setShipAddressId(orderRequest.getShipAddressId());
		order.setPaymentId(orderRequest.getPaymentId());
		order.setCreatedAt(LocalDateTime.now());
		order.setUpdatedAt(LocalDateTime.now());

		Order saveOrder = orderRepository.save(order);
		return viewOrder(saveOrder.getOrderId());
	}

	public Order fallbackCreateOrder(Order orderRequest, Throwable t) {
		log.error("Circuit Breaker triggered for createOrder: {}", t.getMessage(), t);

		Order fallbackOrder = new Order();
		fallbackOrder.setOrderId(orderRequest.getOrderId());
		fallbackOrder.setTotalAmount(orderRequest.getTotalAmount());
		fallbackOrder.setStatus("FAILED");
		fallbackOrder.setCreatedAt(LocalDateTime.now());
		fallbackOrder.setUpdatedAt(LocalDateTime.now());

		log.error("Order creation failed, returning fallback order with status 'FAILED'.");
		return fallbackOrder;
	}

	@Transactional
	@CircuitBreaker(name = "${spring.application.name}", fallbackMethod = "fallbackUpdateOrder")
	public Order updateOrder(Order orderRequest, Integer orderId) throws Exception {
		log.info("Updating order with orderId {}", orderRequest);

		Order order = orderRepository.findById(orderId)
				.orElseThrow(() -> new RuntimeException("Order not found for OrderID: " + orderId));


		order.setStatus(orderRequest.getStatus());
		order.setShipAddressId(orderRequest.getShipAddressId());
		order.setPaymentId(orderRequest.getPaymentId());
		order.setUpdatedAt(LocalDateTime.now());

		log.info("Saving the updated order.");
		Order updateOrder = orderRepository.save(order);
		return viewOrder(updateOrder.getOrderId());
	}

	public Order fallbackUpdateOrder(Integer orderId, Double totalAmount, String status, Throwable t) {
		log.error("Circuit Breaker triggered for updateOrder: {}", t.getMessage(), t);

		Order fallbackOrder = new Order();
		fallbackOrder.setOrderId(orderId);
		fallbackOrder.setTotalAmount(totalAmount);
		fallbackOrder.setStatus("FAILED");
		fallbackOrder.setUpdatedAt(LocalDateTime.now());

		log.error("Order update failed, returning fallback order with status 'FAILED'.");
		return fallbackOrder;
	}
	@Transactional
	@CircuitBreaker(name = "${spring.application.name}", fallbackMethod = "fallbackCreateOrder")
	public Order viewOrder(Integer orderId) throws Exception {
		log.info("Fetching order with ID {}", orderId);
		Order order = orderRepository.findById(orderId)
				.orElseThrow(() -> new RuntimeException("Order not found for OrderID: " + orderId));
		ResponseEntity<ProductDto[]> productEntity = null;
		
		
		Long addressId = order.getShipAddressId();
		Long paymentId = order.getPaymentId();
		if(addressId != null) {
			UserAndAddressDTO userAddress = GetAddressFromUserService(addressId);
			if (userAddress == null || userAddress.getAddress().getAddressId() == null) {
				order.setAddress(null);
			}else {
				order.setAddress(userAddress.getAddress());
			}
		
		}
		if(paymentId != null) {
			PaymentDTO payment = GetPaymentFromPaymentService(paymentId);
			if (payment == null ) {
				order.setPayment(null);
			}else {
				order.setPayment(payment);
			}
		
		}
		
		if(order.getCartItem()!=null) {
		List<Long> productIds = order.getCartItem().stream().map(a -> a.getProductId()).toList();
		
		if (productIds != null && !productIds.isEmpty()) {
			productEntity = GetProductDataFromCatlogService(productIds);
		
		try {

			HttpStatusCode statusCode = productEntity.getStatusCode();
			if (statusCode == HttpStatus.OK) {
				ProductDto[] productDTO = productEntity.getBody();
				Comparator<ProductDto> cmp = Comparator.comparing(ProductDto::getProductId);
				Arrays.stream(productDTO).sorted(cmp);
				for (int i = 0; i < productDTO.length; i++) {
					if (productDTO[i].getProductId().equals(order.getCartItem().get(i).getProductId())) {
						order.getCartItem().get(i).setProductDTO(productDTO[i]);
					}
				}

			} else {
				ProductDto prddto = new ProductDto();

				order.getCartItem().forEach(a -> a.setProductDTO(prddto));
			}

		} catch (Exception e) {
			log.info("Error in view Cart API and  prodcut service call" + e);
			ProductDto prddto = null;

			order.getCartItem().forEach(a -> a.setProductDTO(prddto));
			throw new RuntimeException("Error in view Cart API and  prodcut service call");
		}
		
		}
		}

		return order;
	}

	// Cancel an order by ID (update its status)
	public Order cancelOrder(Integer orderId) {
		log.info("Cancelling order with ID {}", orderId);
		Order order = orderRepository.findById(orderId).orElse(null);

		if (order != null) {
			order.setStatus("Cancelled");
			log.info("Order with ID {} cancelled successfully.", orderId);
			return orderRepository.saveAndFlush(order);
		}
		log.warn("Order with ID {} not found for cancellation.", orderId);
		return null;
	}

	@Transactional
	@CircuitBreaker(name = "${spring.application.name}", fallbackMethod = "fallbackCreateOrder")
	public Order placeOrder(List<CartItem> cartItems, Integer orderId) {

		Order order = orderRepository.findById(orderId).orElse(null);

		if (order != null) {
			log.info("Order {}", order);
			for (CartItem item : cartItems) {

				item.setOrder(order);
				log.info("Order {} item: {}", order, item);
			}
			order.setCartItem(cartItems);
			order.setStatus("Placed");
			double totalValue = order.getCartItem().stream()
                    .mapToDouble(cartItem -> cartItem.getQuantity() * cartItem.getProductDTO().getPrice())
                    .sum();
			 order.setTotalAmount(totalValue);

				try {
					// Fetching tax amount from the external Tax Service
					Double totalAmount = order.getTotalAmount();
					log.info("Calling Tax Service API to fetch tax for totalAmount: {}", totalAmount);
					Double taxAmount = restTemplate.getForObject(TaxServiceAPI, Double.class, totalAmount);
		
					if (taxAmount != null && taxAmount instanceof Double) {
						order.setTax(taxAmount);
					} else {
						log.warn("Received invalid tax amount: {}", taxAmount);
						throw new RuntimeException("Error fetching tax amount from Tax Service");
					}
				} catch (Exception e) {
					log.error("Error while calling Tax Service API", e);
					throw new RuntimeException("Failed to fetch tax from Tax Service", e);
				}
				List<Long> cartItemIds = order.getCartItem().stream().map(a -> a.getCartId()).toList();
				clearCart(cartItemIds);
			 
			Order savedOrder = orderRepository.save(order);
			try {
				return viewOrder(savedOrder.getOrderId());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return savedOrder;				
			}
		} else {
			new RuntimeException("Order not found for OrderID: " + orderId);
			return null;
		}
	}

	private ResponseEntity<ProductDto[]> GetProductDataFromCatlogService(List<Long> list) {
		log.info("****Rest Templet microservice  calling  method GetProductDataFromCatlogService");
		String url = "http://catalog-service/api/products/by-ids";// product API
		ResponseEntity<ProductDto[]> productEntity = null;

		return productEntity= restTemplate.postForEntity(url, list, ProductDto[].class);

	}
	private UserAndAddressDTO GetAddressFromUserService(Long id) {
		log.info("****Rest Templet microservice  calling  method GetAddressFromUserService");
		try {
			UserAndAddressDTO userAddress = restTemplate.getForObject(UserAddressServiceAPI, UserAndAddressDTO.class,id);
			return userAddress;
		} catch (Exception e) {
			// Log the exception and rethrow it so that the circuit breaker can handle it
			log.error("Error fetching user details for userId: {}. Exception: {}", id, e.getMessage(), e);
			return null;
		}
		

	}
	private PaymentDTO GetPaymentFromPaymentService(Long id) {
		log.info("****Rest Templet microservice  calling  method GetPaymentFromPaymentService");
		try {
			PaymentDTO payment = restTemplate.getForObject(PaymentServiceAPI, PaymentDTO.class,id);
			return payment;
		} catch (Exception e) {
			// Log the exception and rethrow it so that the circuit breaker can handle it
			log.error("Error fetching payment: {}. Exception: {}", id, e.getMessage(), e);
			return null;
		}
		

	}
	private void clearCart(List<Long> ids) {
		log.info("****Rest Templet microservice  calling  method clearCart");
		try {
			 restTemplate.put(CartServiceAPI, ids);
			
		} catch (Exception e) {
			// Log the exception and rethrow it so that the circuit breaker can handle it
			log.error("Error clear cart: {}. Exception: {}", ids, e.getMessage(), e);
			
		}
	}
	

}
