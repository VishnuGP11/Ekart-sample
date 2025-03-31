package com.infy.payment_service.exception;

public class ResourceNotFoundException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	ResourceNotFoundException(String message)
	{
		super(message);
	}
}
