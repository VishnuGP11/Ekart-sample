package com.infy.checkout;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import com.infy.checkout.entity.Order;
import com.infy.checkout.repository.OrderRepository;
import com.infy.checkout.service.OrderService;

@SpringBootTest
class CheckoutApplicationTests {

//	@Test
//	void contextLoads() {
//	}
//
//	 @Mock
//	    private OrderRepository orderRepository;
//
//	    @InjectMocks
//	    private OrderService orderService;
//
//	    private Order order;
//
//	    @BeforeEach
//	    void setUp() {
//	        MockitoAnnotations.openMocks(this);
//
//	        order = new Order();
//	        order.setCustomerId(1);
//	        order.setTotalAmount(100.0);
//	        order.setStatus("PENDING");
//	        order.setCreatedAt(LocalDateTime.now());
//	        order.setUpdatedAt(LocalDateTime.now());
//	    }
//
//	    @Test
//	    void testCreateOrder() {
//	        // Arrange
//	        when(orderRepository.save(any(Order.class))).thenReturn(order);
//
//	        // Act
//	        Order createdOrder = orderService.createOrder(1, 100.0, "PENDING");
//
//	        // Assert
//	        assertNotNull(createdOrder);
//	        assertEquals(1, createdOrder.getCustomerId());
//	        assertEquals(100.0, createdOrder.getTotalAmount());
//	        assertEquals("PENDING", createdOrder.getStatus());
//	        assertNotNull(createdOrder.getCreatedAt());
//	        assertNotNull(createdOrder.getUpdatedAt());
//
//	        // Verify interactions with the repository
//	        verify(orderRepository, times(1)).save(any(Order.class));
//	    }
//
//	    @Test
//	    void testUpdateOrder() {
//	        // Arrange
//	        when(orderRepository.findById(1)).thenReturn(Optional.of(order));
//	        when(orderRepository.save(any(Order.class))).thenReturn(order);
//
//	        // Act
//	        Order updatedOrder = orderService.updateOrder(1, 150.0, "COMPLETED");
//
//	        // Assert
//	        assertNotNull(updatedOrder);
//	        assertEquals(150.0, updatedOrder.getTotalAmount());
//	        assertEquals("COMPLETED", updatedOrder.getStatus());
//	        assertNotNull(updatedOrder.getUpdatedAt());
//
//	        // Verify interactions with the repository
//	        verify(orderRepository, times(1)).findById(1);
//	        verify(orderRepository, times(1)).save(any(Order.class));
//	    }
//
//	    @Test
//	    void testUpdateOrderOrderNotFound() {
//	        // Arrange
//	        when(orderRepository.findById(1)).thenReturn(Optional.empty());
//
//	        // Act & Assert
//	        RuntimeException thrown = assertThrows(RuntimeException.class, () -> {
//	            orderService.updateOrder(1, 150.0, "COMPLETED");
//	        });
//
//	        assertEquals("Order not found", thrown.getMessage());
//
//	        // Verify interactions with the repository
//	        verify(orderRepository, times(1)).findById(1);
//	    }
}
