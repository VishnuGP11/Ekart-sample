package com.springboot.catalog_service.exception;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ErrorRespone> handleResouceNotFound(ResourceNotFoundException exception) {
		ErrorRespone errorRespone = new ErrorRespone("RESOURCE_NOT_FOUND", exception.getMessage());
		return new ResponseEntity<>(errorRespone, HttpStatus.NOT_FOUND);

	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorRespone> handleGenericException(Exception exception) {
		ErrorRespone errorRespone = new ErrorRespone("INTERNAL_SERVER_ERROR", "An unexpected error occurred");
		return new ResponseEntity<>(errorRespone, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorRespone> handleValidException(MethodArgumentNotValidException exception) {
		BindingResult bindingResult = exception.getBindingResult();
		List<String> errors = bindingResult.getAllErrors().stream().map(error -> error.getDefaultMessage())
				.collect(Collectors.toList());
		ErrorRespone errorRespone = new ErrorRespone("VALIDATION_FAILED", errors.toString());
		return new ResponseEntity<>(errorRespone, HttpStatus.BAD_REQUEST);
	}

}
