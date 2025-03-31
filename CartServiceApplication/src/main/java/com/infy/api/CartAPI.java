package com.infy.api;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.ArrayList;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.infy.dto.CartIteamDto;
import com.infy.dto.ProductDto;
import com.infy.exception.CartException;
import com.infy.repository.CartRepository;
import com.infy.service.CartService;
import com.infy.service.CartServiceImpl;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.transaction.Transactional;




@RestController
@RequestMapping("/api")

public class CartAPI {

	@Autowired(required=true)
	private CartService cartService;
	

	@Autowired
	private RestTemplate restTemplate;
	
	private static final Log logger = LogFactory.getLog(CartAPI.class);

	
	@Operation(summary = "Get Cart data for view  REST API", description = "This API is used view cart item for  a user.")
	@GetMapping("/cart/getCartItemByUserID/{UserID}")
	@CircuitBreaker(name = "ProductService", fallbackMethod = "ProductServiceCallingFallBack")
	@Retry(name = "ProductService", fallbackMethod = "ProductServiceCallingFallBack")
	public ResponseEntity<List<CartIteamDto>> getCartItemByPersonID(@PathVariable Long UserID)
			throws CartException {
		List<CartIteamDto> crtdto = null;
		ResponseEntity<ProductDto[]> forEntity = null;
		crtdto = cartService.findAllActiveCartDtls(UserID);
		//Double TotalpriceCart = crtdto.stream().mapToDouble(a->a.getTotalPrice()).sum();
		List<Long> list = crtdto.stream().map(a -> a.getProductId()).toList();
		if (list != null && !list.isEmpty()) {
			forEntity = GetProductDataFromCatlogService(list);
		}
		try {
			
			HttpStatusCode statusCode = forEntity.getStatusCode();
			if (statusCode == HttpStatus.OK) {
				ProductDto[]  productDTO = forEntity.getBody();
				Comparator<ProductDto> cmp = Comparator.comparing(ProductDto::getProductId);
				Arrays.stream(productDTO).sorted(cmp);
				for (int i = 0; i < productDTO.length; i++) {
					if (productDTO[i].getProductId().equals(crtdto.get(i).getProductId())) {
						crtdto.get(i).setProductDTO(productDTO[i]);
					}
				}
				
				return new ResponseEntity<>(crtdto, HttpStatus.OK);
			} else {
				ProductDto prddto = new ProductDto();
				
				
				crtdto.forEach(a -> a.setProductDTO(prddto));
				return new ResponseEntity<>(crtdto, HttpStatus.OK);			
				}
 
		} catch (Exception e) {
			logger.info("Error in view Cart API and  prodcut service call" + e);
			ProductDto prddto = null;
 
			crtdto.forEach(a -> a.setProductDTO(prddto));
			return new ResponseEntity<>(crtdto, HttpStatus.INTERNAL_SERVER_ERROR);
		}
 
	}
	
	private ResponseEntity<ProductDto[]> GetProductDataFromCatlogService(List<Long> list) {
		logger.info("****Rest Templet microservice  calling  method GetProductDataFromCatlogService");
		String url = "http://localhost:8080/api/products/by-ids";// product API
		ResponseEntity<ProductDto[]> forEntity = null;
		
		return forEntity = restTemplate.postForEntity(url, list, ProductDto[].class);
 
	}
	
	ResponseEntity<List<CartIteamDto>> ProductServiceCallingFallBack(Exception e) {
		ProductDto[] prodDtoArr = null;
		logger.info("***ProductServiceCallingFallBack1 calling");
		List<CartIteamDto> crtdto = new ArrayList<CartIteamDto>();
 
		return new ResponseEntity<>(crtdto, HttpStatus.INTERNAL_SERVER_ERROR);
 
	}

	@PostMapping("/cart/SaveCartItem/AddintoCart")
    public ResponseEntity<String> addItemToCart(@RequestBody CartIteamDto cartIteamDto) throws CartException 
	{
            String message = cartService.addItemToCart(cartIteamDto);
    		return new ResponseEntity<>(message, HttpStatus.CREATED);
        } 

	@PutMapping("/cart/items/update")
	public ResponseEntity<String> updateItem(@RequestBody CartIteamDto cartIteamDto) throws CartException 
		{
	            String message = cartService.updateItem(cartIteamDto);
	    		return ResponseEntity.ok(message);
	        } 
	
	@DeleteMapping("cart/clearcartById/{CartId}")
	public ResponseEntity<String> clearCartById(@PathVariable Long CartId) throws CartException{
		
            String message = cartService.clearCartById(CartId);
    		return ResponseEntity.ok(message);
        } 
	
	@Operation(summary = "Remove items in  Cart REST API", description = "This API is used to Remove items in  Cart post order placed .")
	@PutMapping("cart/clearcart")
	public ResponseEntity<String> clearCart(@RequestBody List<Long> CartIds) throws Exception {
			String message1 = "Cart item not deleted for  User";
			String message = "Cart item deleted for  User";
			int clearCartByIds = cartService.clearCartByIds(CartIds);
			if (clearCartByIds>1) {
				return ResponseEntity.ok(message);
			} else
	 
				return new ResponseEntity<>(message1, HttpStatus.INTERNAL_SERVER_ERROR);
		}


	
	
}
