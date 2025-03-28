package com.infy.user.exceptions;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.MethodArgumentNotValidException;


import com.infy.user.util.UserConstants;

@RestControllerAdvice
public class GlobalExceptionHandler {
	
	private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
	
	@Autowired
    private Environment environment;
	
	@ExceptionHandler(Exception.class) 
    public ResponseEntity<ErrorMessage> generalExceptionHandler(Exception ex) 
    {
         ErrorMessage error = new ErrorMessage(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage());
         error.setMessage(environment.getProperty(UserConstants.GENERAL_EXCEPTION_MESSAGE.toString()));
         return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
    
    @ExceptionHandler({InvalidInputException.class, NotNullUserFieldsException.class})
    public ResponseEntity<ErrorMessage> handleBADRequestExceptions(Exception ex) {
    	ErrorMessage errorResponse = new ErrorMessage( HttpStatus.BAD_REQUEST.value(), ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ErrorMessage> handleAlreadyExistsException(UserAlreadyExistsException ex) {
    	ErrorMessage errorResponse = new ErrorMessage(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({AddressNotFoundException.class, UserNotFoundException.class, CustomerNotFoundException.class})
    public ResponseEntity<ErrorMessage> handleNotFoundExceptions(Exception ex) {
    	ErrorMessage errorResponse = new ErrorMessage(HttpStatus.NOT_FOUND.value(), ex.getMessage());
        return new ResponseEntity<ErrorMessage>(errorResponse, HttpStatus.NOT_FOUND);
    }
    
//    @ExceptionHandler({MethodArgumentNotValidException.class})
//    public ResponseEntity<ErrorMessage> handleArgumentNotValidExceptions(MethodArgumentNotValidException ex) {
//    	ErrorMessage errorResponse = new ErrorMessage(HttpStatus.NOT_FOUND.value(), ex.getMessage());
//        return new ResponseEntity<ErrorMessage>(errorResponse, HttpStatus.NOT_FOUND);
//    }
    
//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    public ResponseEntity<ErrorMessage> handleInvalidInputException(MethodArgumentNotValidException ex) {
//    	ErrorMessage errorResponse = new ErrorMessage(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
//        logger.error("Validation error occurred: {}", ex.getMessage());
//        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
//    }
    
//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    public ResponseEntity<ErrorMessage> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
//        logger.error("Validation Exception occurred", ex);
//
//        String errorMessage = ex.getBindingResult().getFieldErrors().stream()
//            .map(FieldError::getDefaultMessage) // Ensure you're using the correct method
//            .filter(Objects::nonNull) // Filter out any null messages
//            .collect(Collectors.joining(", ")); // Join with a comma
//
//        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), errorMessage);
//        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
//    }
    
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorMessage> handleValidationExceptions(MethodArgumentNotValidException ex) {
        // You might want to set a specific error code for validation errors
        int errorCode = HttpStatus.BAD_REQUEST.value(); // Example: 400 for bad request

        // Construct the error message. You can customize this as needed.
        StringBuilder errorMessage = new StringBuilder();
        ex.getBindingResult().getFieldErrors().forEach(error -> {
            errorMessage.append(error.getDefaultMessage())
                        .append("; ");
        });

        // Create an instance of ErrorMessage
        ErrorMessage errorResponse = new ErrorMessage(errorCode, errorMessage.toString());
        logger.error("MethodArgumentNotValidException: {}", errorMessage.toString());

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
   
    
    
}
