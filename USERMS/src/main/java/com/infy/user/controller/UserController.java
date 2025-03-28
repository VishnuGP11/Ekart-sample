package com.infy.user.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.infy.user.dto.ProductAttrDto;
import com.infy.user.dto.ProductDto;
import com.infy.user.dto.UserAndAddressDTO;
import com.infy.user.dto.UserDTO;
import com.infy.user.entity.User;
import com.infy.user.exceptions.AddressNotFoundException;
import com.infy.user.exceptions.CustomerNotFoundException;
import com.infy.user.exceptions.InvalidInputException;
import com.infy.user.exceptions.NotNullUserFieldsException;
import com.infy.user.exceptions.UserAlreadyExistsException;
import com.infy.user.exceptions.UserNotFoundException;
import com.infy.user.service.AddressService;
import com.infy.user.service.UserService;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	private UserService userService;
	@Autowired
	 private ModelMapper modelMapper;
	
	@Autowired(required = true)
	private RestTemplate restTemplate;
	
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);
	
	private static final String catalog_url="https://192.168.0.104:8080/api/products/by-ids";
	
	@GetMapping("/")
	public ResponseEntity<String> test() {
		return ResponseEntity.ok("Service is running");
	}
	@PostMapping("/addUser")
	@ApiResponse(description = "Post method for adding user details")
	public ResponseEntity<UserDTO> addUser(@Valid @RequestBody User user) throws NotNullUserFieldsException, UserAlreadyExistsException, MethodArgumentNotValidException{
		UserDTO createdUser = userService.addUser(user);
		return new ResponseEntity<UserDTO>(createdUser, HttpStatus.CREATED);
	}
	
	@PutMapping("/{id}")
	@ApiResponse(description = "Put method for updating user details")
	public ResponseEntity<UserDTO> updateUser(@PathVariable("id") Long userId, @RequestBody UserDTO user) throws CustomerNotFoundException, InvalidInputException{
		UserDTO updatedUser = userService.updateUser(userId, user);
		return new ResponseEntity<UserDTO>(updatedUser,HttpStatus.OK);
	}
	
	@GetMapping("/{userId}")
	@ApiResponse(description = "Get method for fetching user details")
	@CircuitBreaker(name="userService", fallbackMethod="getUserByIdFallBack")
	public ResponseEntity<?> getUserById(@PathVariable Long userId) throws UserNotFoundException {
	    logger.info("Received request to get user {}", userId);

	    User user = userService.getUserById(userId)
	            .orElseThrow(() -> new UserNotFoundException("User not found for ID: " + userId));

	    // This request to the catalog_url will time out and trigger the circuit breaker
	  //  ProductDto productDTO = restTemplate.getForObject(catalog_url, ProductDto.class);

	    logger.info("User found successfully");

	    // Return user details
	    UserDTO userDTO = modelMapper.map(user, UserDTO.class);
	    return new ResponseEntity<>(userDTO, HttpStatus.OK);
	}

	
	//To get user details along with address details
			@GetMapping("/address/{addressId}")
			@ApiResponse(description = "Get method for fetching user details along with address details")
			public ResponseEntity<UserAndAddressDTO> getAddressAndUser(@PathVariable int addressId) throws AddressNotFoundException{
				return new ResponseEntity<>(userService.getAddressAndUser(addressId), HttpStatus.OK);
			}
			
			public ResponseEntity<UserDTO> getUserServiceFallback(Long userId, Throwable throwable) {
			    logger.warn("Fallback method invoked due to: {}", throwable.getMessage());

			    // Create a default UserDTO as per your requirements
			    UserDTO fallbackUser = new UserDTO();
			    fallbackUser.setUsername("Fallback User");
			    fallbackUser.setEmail("fallback@example.com");
			    // Optionally set default products or leave it empty

			    return new ResponseEntity<>(fallbackUser, HttpStatus.OK);
			}
			
			public ResponseEntity<?> getUserByIdFallBack(Long userId, Throwable throwable) {
			    logger.warn("Fallback method called for user ID: " + userId + " due to: " + throwable.getMessage());

			    // Handle the fallback response
			    UserDTO fallbackUser = new UserDTO();
			    fallbackUser.setUserId(userId);
			    fallbackUser.setFirstName("Fallback User");
			    // Set other default values as needed

			    return new ResponseEntity<>(fallbackUser, HttpStatus.SERVICE_UNAVAILABLE);
			}

}
