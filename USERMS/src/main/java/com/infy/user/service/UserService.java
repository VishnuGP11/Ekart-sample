package com.infy.user.service;

import java.util.Optional;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.MethodArgumentNotValidException;

import com.infy.user.dto.AddressDTO;
import com.infy.user.dto.UserAndAddressDTO;
import com.infy.user.dto.UserDTO;
import com.infy.user.entity.Address;
import com.infy.user.entity.User;
import com.infy.user.exceptions.AddressNotFoundException;
import com.infy.user.exceptions.CustomerNotFoundException;
import com.infy.user.exceptions.InvalidInputException;
import com.infy.user.exceptions.NotNullUserFieldsException;
import com.infy.user.exceptions.UserAlreadyExistsException;
import com.infy.user.exceptions.UserNotFoundException;
import com.infy.user.repository.AddressRepository;
import com.infy.user.repository.UserRepository;
import com.infy.user.util.UserConstants;

@Service
@PropertySource("classpath:ValidationMessages.properties")
public class UserService {

	private static final Logger logger = LoggerFactory.getLogger(UserService.class);
	@Autowired
	private UserRepository userRepo;
	 @Autowired
	 private ModelMapper modelMapper;
	 @Autowired
	    private Environment environment;
	 @Autowired
		AddressRepository addressRepository;
	
	 public Optional<User> getUserById(Long id) throws UserNotFoundException {
//		 if(id == 3) {
//			logger.error("Runt time exception"); 
//			throw new RuntimeException("Run time exception");
//		 }
 	    Optional <User> user = userRepo.findById(id);
 	    
 	    if(user.isEmpty()) {
 	    	logger.error("Error occured: User not found for this specified id: {}",id+" "+environment.getProperty(UserConstants.USER_NOT_FOUND.toString()));
 	    	throw new UserNotFoundException(environment.getProperty(UserConstants.USER_NOT_FOUND.toString()));
 	    }
 	    logger.info("User found for user id: "+id);
 	    return user;
 }
	public UserDTO addUser(User user) throws NotNullUserFieldsException, UserAlreadyExistsException, MethodArgumentNotValidException{
		
    	 if(user.getUserType().equals("G")) {
    		logger.info("User is guest", user);
    	}
    	 if(userExistsByUsername(user.getUsername())) {
    		logger.error("Error occurred: User already exists with same username: {}", user.getUsername()+" "+environment.getProperty(UserConstants.USER_ALREADY_EXISTS.toString()));
    		throw new UserAlreadyExistsException(environment.getProperty(UserConstants.USER_ALREADY_EXISTS.toString())+" by same username");
    	}
    	 else if(userExistsByEmail(user.getEmail())) {
    		logger.error("Error occurred: User already exists with same email: {}", user.getEmail()+" "+environment.getProperty(UserConstants.USER_ALREADY_EXISTS.toString()));
    		throw new UserAlreadyExistsException(environment.getProperty(UserConstants.USER_ALREADY_EXISTS.toString())+" by same email");
    	}
    	 else if(userExistsByPhoneNumber(user.getPhone())) {
    		logger.error("Error occurred: User already exists with phone number: {}", user.getPhone()+" "+environment.getProperty(UserConstants.USER_ALREADY_EXISTS.toString()));
    		throw new UserAlreadyExistsException(environment.getProperty(UserConstants.USER_ALREADY_EXISTS.toString())+" by same phone number");
    	}
    	logger.info("User added successfully");
    	return modelMapper.map(userRepo.save(user), UserDTO.class);
    }
	
	
	public UserDTO updateUser(Long userId, UserDTO userDetails) throws CustomerNotFoundException, InvalidInputException {
		if(userDetails.getEmail() == null || userDetails.getFirstName() == null || userDetails.getLastName() == null || userDetails.getUsername() == null || userDetails.getPhone() == null )
    	{
    		logger.error("Invalid input, all fields must be provided {}:"+environment.getProperty(UserConstants.CUSTOMER_INVALID_INPUT.toString()));
    		throw new InvalidInputException(environment.getProperty(UserConstants.CUSTOMER_INVALID_INPUT.toString()));
    	}
		logger.info("Input is fine");
		Optional<User> user = userRepo.findById(userId);
		User existingUser = null;
		if(user.isPresent()) {
			logger.info("User Present");
			existingUser = user.get();
			existingUser.setEmail(userDetails.getEmail());
			existingUser.setUsername(userDetails.getUsername());
			existingUser.setPassword(userDetails.getPassword());
			existingUser.setFirstName(userDetails.getFirstName());
			existingUser.setLastName(userDetails.getLastName());
			existingUser.setPhone(userDetails.getPhone());
		}else
        {
			logger.error(environment.getProperty(UserConstants.USER_NOT_FOUND.toString())+" for user Id: "+userId);
            throw new CustomerNotFoundException(environment.getProperty(UserConstants.CUSTOMER_NOT_FOUND.toString()));
			
        }
		logger.info("User to be saved");
		return modelMapper.map(userRepo.save(existingUser), UserDTO.class);
		
	}
	
public UserAndAddressDTO getAddressAndUser(long addressId) throws AddressNotFoundException {
		
		Optional<Address> optAddress = addressRepository.findById(addressId);
		logger.info("Record fetched from database successfully.");
		
		UserAndAddressDTO response = new UserAndAddressDTO();
		
		if(optAddress.isPresent()) {

			Address address = optAddress.get();
			UserDTO userDTO = new UserDTO();
			userDTO.setUserId(address.getUser().getUserId());
			userDTO.setUsername(address.getUser().getUsername());
			userDTO.setFirstName(address.getUser().getFirstName());
			userDTO.setLastName(address.getUser().getLastName());
			userDTO.setEmail(address.getUser().getEmail());
			userDTO.setPhone(address.getUser().getPhone());
			
			AddressDTO addressDTO = new AddressDTO();
			addressDTO.setAddressId(address.getAddressId());
			addressDTO.setStreet(address.getStreet());
			addressDTO.setAddressLine(address.getAddressLine());
			addressDTO.setAddressType(address.getAddressType());
			addressDTO.setCity(address.getCity());
			addressDTO.setZipCode(address.getZipcode());
			addressDTO.setState(address.getState());
			addressDTO.setCountry(address.getCountry());
			
			response.setStatus("SUCCESS");
			response.setAddress(addressDTO);
			response.setUser(userDTO);
		}
		else {
			logger.error(environment.getProperty(UserConstants.ADDRESS_NOT_FOUND.toString())+" for addressId : "+addressId);
			throw new AddressNotFoundException(environment.getProperty(UserConstants.ADDRESS_NOT_FOUND.toString()));
		}
		logger.info("Fetched user details for that address Id"+addressId);
		return response;
	}

	
	 public boolean userExistsByUsername(String username) {
	    	return userRepo.existsByUsername( username);
	    }
	    
	    public boolean userExistsByPhoneNumber(String phone) {
	    	return userRepo.existsByPhone(phone);
	    }
	    public boolean userExistsByEmail(String email) {
	    	return userRepo.existsByEmail(email);
	    }
	    public boolean userExistsById(Long id) {
	    	return userRepo.existsById(id);
	    }
}
