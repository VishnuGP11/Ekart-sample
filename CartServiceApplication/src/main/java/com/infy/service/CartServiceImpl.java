package com.infy.service;

import java.util.Optional;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestTemplate;

import com.infy.dto.CartIteamDto;
import com.infy.dto.ProductDto;

import com.infy.exception.CartException;
import com.infy.repository.CartRepository;
import com.infy.utility.ExceptionConstants;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.transaction.Transactional;

@Service
public class CartServiceImpl implements CartService{
	
	@Autowired
	private CartRepository cartRepository;
	
	
	
	private static final Log logger = LogFactory.getLog(CartServiceImpl.class);

	@Override
		public List<CartIteamDto> findAllActiveCartDtls(Long userID) throws CartException {
			String message = "Cart item data not  found  for User:" + userID;
			 Optional<List<CartIteamDto>> allActiveCartDtls = cartRepository.findAllActiveCartDtls(userID);
			if (allActiveCartDtls.get() == null || allActiveCartDtls.get().isEmpty()) {
			
				throw new CartException(message);
			}
			return allActiveCartDtls.get();
			
		}
	
	@Override
	public String addItemToCart(CartIteamDto cartIteamDto) throws CartException {
	    logger.info("addItemToCart method to add the details to DB");
	    
	    logger.info("Checking if cart with userId and productId already exists");
	    Optional<CartIteamDto> cartOptional = cartRepository.findByUserIdAndProductId(cartIteamDto.getUserId(), cartIteamDto.getProductId());
	    if (cartOptional.isPresent()) 
	        {
	        	CartIteamDto existingCart = cartOptional.get();
	            
	            logger.info("Updating existing cart item");
	            existingCart.setQuantity(existingCart.getQuantity() + cartIteamDto.getQuantity());
	            existingCart.setTotalPrice(existingCart.getQuantity() * cartIteamDto.getPrice());
	            existingCart.setLstUpdtDate(new Date());
	            cartRepository.save(existingCart);
	        } 
	        else
	        {
	            logger.info("Adding new item to existing cart");
	            CartIteamDto newCartItem = createNewCartItem(cartIteamDto);
	            cartRepository.save(newCartItem);
	        }

	    return "Item added to cart successfully";
	}
	
	private CartIteamDto createNewCartItem(CartIteamDto cartIteamDto) {
	    CartIteamDto newCartItem = new CartIteamDto();
	    newCartItem.setUserId(cartIteamDto.getUserId());
	    newCartItem.setProductId(cartIteamDto.getProductId());
	    newCartItem.setPrice(cartIteamDto.getPrice());
	    newCartItem.setQuantity(cartIteamDto.getQuantity());
	    newCartItem.setTotalPrice(cartIteamDto.getQuantity() * cartIteamDto.getPrice());
	    newCartItem.setCreatedDate(new Date());
	    newCartItem.setLstUpdtDate(new Date());
	    newCartItem.setIsOrderPlaced(0);
	    newCartItem.setIsActive(1);
	    return newCartItem;
	}
	

	
	
	@Override
	@Transactional
	public String updateItem(CartIteamDto cartIteamDto) throws CartException {
	    logger.info("updateItem method to update the quanity of the item");

	    cartIteamDto.setTotalPrice(cartIteamDto.getPrice()*cartIteamDto.getQuantity());
	    cartIteamDto.setLstUpdtDate(new Date());
		cartRepository.save(cartIteamDto);
		
	    logger.info("Quantity is updated successfully for cart item");
		return "Quantity is updated successfully";
	}

	@Override
	@Transactional
	public int clearCartByIds(List<Long> cartId) {
		return cartRepository.clearCartByIds(cartId);
	}

	@Override
	public String clearCartById(Long cartId) throws CartException {
		cartRepository.deleteById(cartId);

		return "Item is removed from cart with cartId:"+cartId;

	}
	


	
	

}
