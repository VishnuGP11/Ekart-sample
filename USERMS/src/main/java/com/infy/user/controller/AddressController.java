package com.infy.user.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;

import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
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


import com.infy.user.dto.AddressDTO;
import com.infy.user.dto.AddressResponse;
import com.infy.user.entity.Address;
import com.infy.user.exceptions.AddressNotFoundException;
import com.infy.user.exceptions.InvalidInputException;
import com.infy.user.exceptions.UserNotFoundException;
import com.infy.user.exceptions.UserNotRelatedToAddressException;
import com.infy.user.service.AddressService;
import com.infy.user.util.UserConstants;

import io.swagger.v3.oas.annotations.responses.ApiResponse;

 
@RestController

@RequestMapping("/users")

public class AddressController {
	
	private static final Logger logger = LoggerFactory.getLogger(AddressController.class);
	
	@Autowired
	AddressService addressService;
	
	@Autowired
	private Environment environment;

	@PostMapping("/{userId}/address")
	@ApiResponse(description = "Post method for adding address details of that user")
	ResponseEntity<AddressDTO> createAddress(@PathVariable Long userId, @RequestBody Address address) throws UserNotFoundException {
		logger.info("Calling AddressController to add new addresss");
		try {
			AddressDTO savedAddress = addressService.saveAddress(userId, address);
			return new ResponseEntity<>(savedAddress, HttpStatus.CREATED);
		} catch (Exception e) {
			logger.error("Exceptionaddress" + e);
			return new ResponseEntity<AddressDTO>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	// api to update user address 
		// http://localhost:8080/users/{userId}/address/{addressId}
		@PutMapping("/{userId}/address/{addressId}")
		@ApiResponse(description = "Put method for updating address details of that user")
	    public ResponseEntity<AddressDTO> updateAddressById(
	            @PathVariable Long userId, 
	            @PathVariable Long addressId, 
	            @RequestBody Address addressDetails) throws UserNotFoundException, AddressNotFoundException, UserNotRelatedToAddressException, InvalidInputException {
	        
	        // Call the service method to update the address
			AddressDTO updatedAddress = addressService.updateAddressById(addressId, userId, addressDetails);
	        
	        // Prepare the response
	       
	        updatedAddress.setMessage("updated user address details");
	        logger.info("User address updated successfully for this userId :{}"+userId);
	        return new ResponseEntity<>(updatedAddress, HttpStatus.OK);
	    }	 
		// api to delete user address 
		// http://localhost:8080/users/{userId}/address/{addressId}
		@DeleteMapping("/{userId}/address/{addressId}")
		@ApiResponse(description = "Delete method for deleting address details of that user")
	    public ResponseEntity<Map<String, String>> removeAddress(
	            @PathVariable Long userId, 
	            @PathVariable Long addressId) throws UserNotFoundException, AddressNotFoundException {
	        
	        // Call the service method to remove the address
	        Address deletedAddress = addressService.removeAddress(userId, addressId);

	        // Prepare the success response
	        Map<String, String> response = new HashMap<>();
	        response.put("status", "success");
	        response.put("message", "Deleted the address successfully");

	        return new ResponseEntity<>(response, HttpStatus.OK);
	    }
		
		@GetMapping("/{userId}/addressType/{addressType}")
		@ApiResponse(description = "Get method for fetching address details of that user according to address type")
	    public ResponseEntity<AddressResponse> getAddressesByType(
	            @PathVariable Long userId,
	            @PathVariable String addressType) throws InvalidInputException, UserNotFoundException, AddressNotFoundException {
	        
	        List<Address> addresses = addressService.getAddressesByType(userId, addressType);
	        
	        if (addresses.isEmpty()) {
	            logger.error("Address not found with ID: "+ userId+" "+environment.getProperty(UserConstants.ADDRESS_NOT_FOUND.toString()));
	            throw new AddressNotFoundException(environment.getProperty(UserConstants.ADDRESS_NOT_FOUND.toString()));
	        }

	        List<AddressDTO> addressDtos = addresses.stream()
	                .map(AddressDTO::new)
	                .collect(Collectors.toList());
	        AddressResponse successResponse = new AddressResponse("success", addressDtos);
	        logger.info("Fetched address by Type for this userId: "+userId);
	        return ResponseEntity.ok(successResponse);
	    }
		
		@GetMapping("/{userId}/address/")
		@ApiResponse(description = "Get method for fetching address details of that user")
	    public ResponseEntity<AddressResponse> getAddressesByUser(
	            @PathVariable Long userId) throws UserNotFoundException, AddressNotFoundException {
	        
	        List<AddressDTO> addresses = addressService.getAddressesByUser(userId);
	        	        
	        AddressResponse successResponse = new AddressResponse("success", addresses);
	        logger.info("Fetched address for this userId: "+userId);
	        return ResponseEntity.ok(successResponse);
	    }

}
