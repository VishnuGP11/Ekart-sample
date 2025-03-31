package com.infy.utility;

import org.apache.commons.logging.LogFactory;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

import org.apache.commons.logging.Log;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.ConstraintViolation;


import com.infy.exception.CartException;

@RestControllerAdvice
@PropertySource("classpath:message.properties")
public class ExceptionController {

	@Autowired
	private Environment environment;
	
	
	private static final Log logger = LogFactory.getLog(ExceptionController.class);
    
	@ExceptionHandler(CartException.class)
	public ResponseEntity<ErrorInfo> exceptionHandler(CartException exception) {
		logger.error(exception.getMessage(), exception);
	    ErrorInfo errorInfo = new ErrorInfo();
	    errorInfo.setErrorCode(HttpStatus.BAD_REQUEST.value());
	    errorInfo.setErrorMessage(environment.getProperty(exception.getMessage())); // Directly set the exception message
	    errorInfo.setTimeStamp(LocalDateTime.now());
	    return new ResponseEntity<>(errorInfo, HttpStatus.BAD_REQUEST);
	}
	 // add appropriate annotation
	 @ExceptionHandler(Exception.class)
	     public ResponseEntity<ErrorInfo> generalExceptionHandler(Exception exception)
	     {
		 logger.error(exception.getMessage(), exception);
	 	ErrorInfo errorInfo = new ErrorInfo();
	 	errorInfo.setErrorCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
	 	errorInfo.setErrorMessage(environment.getProperty("General.EXCEPTION_MESSAGE"));
	 	//will change to constants.toString()later
	 	errorInfo.setTimeStamp(LocalDateTime.now());
	 	return new ResponseEntity<>(errorInfo,
	 				    HttpStatus.INTERNAL_SERVER_ERROR);
	     }

	     // add appropriate annotation
	 @ExceptionHandler({MethodArgumentNotValidException.class,ConstraintViolationException.class})
	     public ResponseEntity<ErrorInfo> validatorExceptionHandler(Exception exception)
	     {
		 logger.error(exception.getMessage(), exception);
	 	String errorMsg;
	 	if (exception instanceof MethodArgumentNotValidException)
	 	{
	 	    MethodArgumentNotValidException manvException = (MethodArgumentNotValidException) exception;
	 	    errorMsg = manvException.getBindingResult()
	 				    .getAllErrors()
	 				    .stream()
	 				    .map(ObjectError::getDefaultMessage)
	 				    .collect(Collectors.joining(", "));

	 	}
	 	else
	 	{
	 	    ConstraintViolationException cvException = (ConstraintViolationException) exception;
	 	    errorMsg = cvException.getConstraintViolations()
	 				  .stream()
	 				  .map(ConstraintViolation::getMessage)
	 				  .collect(Collectors.joining(", "));

	 	}
	 	ErrorInfo errorInfo = new ErrorInfo();
	 	errorInfo.setErrorCode(HttpStatus.BAD_REQUEST.value());
	 	errorInfo.setErrorMessage(errorMsg);
	 	return new ResponseEntity<>(errorInfo, HttpStatus.BAD_REQUEST);
	     }

}
