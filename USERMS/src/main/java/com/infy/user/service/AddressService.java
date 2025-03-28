package com.infy.user.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.infy.user.dto.AddressDTO;
import com.infy.user.dto.AddressResponse;
import com.infy.user.entity.Address;
import com.infy.user.entity.User;
import com.infy.user.exceptions.AddressNotFoundException;
import com.infy.user.exceptions.InvalidInputException;
import com.infy.user.exceptions.UserNotFoundException;
import com.infy.user.exceptions.UserNotRelatedToAddressException;
import com.infy.user.repository.AddressRepository;
import com.infy.user.repository.UserRepository;
import com.infy.user.util.UserConstants;

import jakarta.transaction.Transactional;

@Service
@PropertySource("classpath:ValidationMessages.properties")
public class AddressService {
	@Autowired
	AddressRepository addressRepository;

	@Autowired
	private Environment environment;

	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	UserRepository userRepository; // Inject the user repository
	
	private static final Logger logger = LoggerFactory.getLogger(AddressService.class);

	@Transactional
	public AddressDTO saveAddress(Long userId, Address address) throws InvalidInputException, UserNotFoundException {
		if (null == address.getAddressType()
				|| (!address.getAddressType().equals("B") && !address.getAddressType().equals("S"))) {
			logger.error("Invalid input for address type: " + address.getAddressType()
					+ environment.getProperty(UserConstants.INVALID_INPUT.toString()));
			throw new InvalidInputException(environment.getProperty(UserConstants.INVALID_INPUT.toString()));
		}
		User user = userRepository.findById(userId)
				.orElseThrow(() -> {
			logger.error("User not found with ID: {}", userId+environment.getProperty(UserConstants.USER_NOT_FOUND.toString()));
			return new UserNotFoundException(environment.getProperty(UserConstants.USER_NOT_FOUND.toString()));
		});

		logger.info("User found: {}", user.getUserId());

		address.setUser(user);
		logger.info("Added address for userId: " + userId);
		return modelMapper.map(addressRepository.save(address), AddressDTO.class);

	}

	@Transactional
	public AddressDTO updateAddressById(Long addressId, Long userId, Address addressDetails)
			throws UserNotFoundException, InvalidInputException, AddressNotFoundException,
			UserNotRelatedToAddressException {

		// Validate input
		if (addressDetails.getStreet() == null || addressDetails.getCity() == null || addressDetails.getState() == null
				|| addressDetails.getZipcode() == null || addressDetails.getCountry() == null) {
			logger.error("Invalid input, all fields must be provided"+environment.getProperty(UserConstants.INVALID_INPUT.toString()));
			throw new InvalidInputException(environment.getProperty(UserConstants.INVALID_INPUT.toString()));
		}

		// Check if the address exists
		Address existingAddress = addressRepository.findById(addressId).orElseThrow(() -> {
			logger.error("Address not found with ID: " + addressId+environment.getProperty(UserConstants.ADDRESS_NOT_FOUND.toString()));
			return new AddressNotFoundException(environment.getProperty(UserConstants.ADDRESS_NOT_FOUND.toString()));
		});

		// check if the user exists
		User user = userRepository.findById(userId).orElseThrow(() -> {
			logger.error("User not found with ID: " + userId+environment.getProperty(UserConstants.USER_NOT_FOUND.toString()));
			return new UserNotFoundException(environment.getProperty(UserConstants.USER_NOT_FOUND.toString()));
		});
		// check if user id corresponds to the address id
		if (user.getUserId() != existingAddress.getUser().getUserId()) {
			logger.error("user id is not matched to this address id : " + addressId+environment.getProperty(UserConstants.USER_NOT_RELATED_TO_ADDRESS.toString()));
			throw new UserNotRelatedToAddressException(environment.getProperty(UserConstants.USER_NOT_RELATED_TO_ADDRESS.toString()));
		}

		// Update address details
		existingAddress.setStreet(addressDetails.getStreet());
		existingAddress.setAddressLine(addressDetails.getAddressLine());
		existingAddress.setCity(addressDetails.getCity());
		existingAddress.setState(addressDetails.getState());
		existingAddress.setZipcode(addressDetails.getZipcode());
		existingAddress.setCountry(addressDetails.getCountry());

		// Save and return updated address
		logger.info("Updated address details for user " + userId);
		return modelMapper.map(addressRepository.save(existingAddress), AddressDTO.class);

	}

	@Transactional
	public Address removeAddress(Long userId, Long addressId) throws UserNotFoundException, AddressNotFoundException {

		logger.info("Entering method removeAddress");

		// Check if the address exists
		Address existingAddress = addressRepository.findById(addressId).orElseThrow(() -> {
			logger.error("Address not found with ID: " + addressId+environment.getProperty(UserConstants.ADDRESS_NOT_FOUND.toString()));
			return new AddressNotFoundException(environment.getProperty(UserConstants.ADDRESS_NOT_FOUND.toString()));
		});

		// Optionally, check if the user exists (if required)
		User user = userRepository.findById(userId).orElseThrow(() -> {
			logger.error("User not found with ID: " + userId+environment.getProperty(UserConstants.USER_NOT_FOUND.toString()));
			return new UserNotFoundException(environment.getProperty(UserConstants.USER_NOT_FOUND.toString()));
		});

		// Delete the address
		addressRepository.delete(existingAddress);
		logger.info("Removed address successfully for addressId:" + addressId);
		return existingAddress; // Return the deleted address
	}

	public List<Address> getAddressesByType(Long userId, String addressType)
			throws InvalidInputException, UserNotFoundException,AddressNotFoundException {
		
		User user = userRepository.findById(userId).orElseThrow(() -> {
			logger.error("User not found with ID: {}", userId+environment.getProperty(UserConstants.USER_NOT_FOUND.toString()));
			return new UserNotFoundException(environment.getProperty(UserConstants.USER_NOT_FOUND.toString()));
		});

		if (!addressType.equals("B") && !addressType.equals("S")) {
			logger.error("Address type provided is invalid : "+addressType+" : "+environment.getProperty(UserConstants.INVALID_INPUT.toString()));
			throw new InvalidInputException(environment.getProperty(UserConstants.INVALID_INPUT.toString())+" for address type");
		}
		Address address = addressRepository.findByUser_UserId(userId);
		if (address == null || address.getAddressType() == null || !address.getAddressType().equals(addressType)) {
		    logger.error("Address not found with ID: " + userId + " and address type " + addressType + " " + environment.getProperty(UserConstants.ADDRESS_NOT_FOUND.toString()));
		    throw new AddressNotFoundException(environment.getProperty(UserConstants.ADDRESS_NOT_FOUND.toString()));
		}
	
		logger.info("Fetched address by type for userId :" + userId + " and address type :" + addressType);
		return addressRepository.findByUser_UserIdAndAddressType(userId, addressType);
	}

	public List<AddressDTO> getAddressesByUser(Long userId) throws UserNotFoundException, AddressNotFoundException {
		
		User user = userRepository.findById(userId).orElseThrow(() -> {
			logger.error("User not found with ID: {}", userId+ environment.getProperty(UserConstants.USER_NOT_FOUND.toString()));
			return new UserNotFoundException(environment.getProperty(UserConstants.USER_NOT_FOUND.toString()));
		});
		List<Address> addressList = addressRepository.findAllByUser_UserId(userId);
		if (addressList.isEmpty()) {
			logger.error("Address not found for user id : "+userId+" " +environment.getProperty(UserConstants.ADDRESS_NOT_FOUND.toString()));
			throw new AddressNotFoundException(environment.getProperty(UserConstants.ADDRESS_NOT_FOUND.toString()));
		}
		List<AddressDTO> addressListDTO = addressList.stream().map(AddressDTO::new).collect(Collectors.toList());
		return addressListDTO;
	}

}
