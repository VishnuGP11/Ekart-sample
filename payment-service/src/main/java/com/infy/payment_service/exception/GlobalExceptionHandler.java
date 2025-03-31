package com.infy.payment_service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    // Handle all RuntimeException instances
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Object> handleRuntimeException(RuntimeException ex, WebRequest request) {
        logger.error("RuntimeException occurred: {}", ex.getMessage(), ex);
        return new ResponseEntity<>(new ErrorResponse("Internal Server Error", ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // Handle ResourceNotFoundException (can be any custom exception you define)
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Object> handleResourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {
        logger.error("ResourceNotFoundException occurred: {}", ex.getMessage(), ex);
        return new ResponseEntity<>(new ErrorResponse("Resource Not Found", ex.getMessage()), HttpStatus.NOT_FOUND);
    }

    // Handle any other exceptions
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request) {
        logger.error("Exception occurred: {}", ex.getMessage(), ex);
        return new ResponseEntity<>(new ErrorResponse("Error", ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // You can also catch and handle specific exceptions like MethodArgumentNotValidException for validation errors
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidationException(MethodArgumentNotValidException ex, WebRequest request) {
        logger.error("Validation failed: {}", ex.getMessage(), ex);
        return new ResponseEntity<>(new ErrorResponse("Validation Failed", ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

}