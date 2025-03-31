package com.infy.exception;

public class CartException extends Exception{

private static final long serialVersionUID = 1L;
	
	public CartException(String message) {
		super(message);
	}
	public CartException(String message, Throwable cause) {
        super(message, cause);
    }
	
}
