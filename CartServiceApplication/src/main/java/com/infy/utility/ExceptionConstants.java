package com.infy.utility;

import org.springframework.context.annotation.PropertySource;

@PropertySource("classpath:message.properties")
public enum ExceptionConstants {

	CART_NOT_FOUND("cart.missing"), 
	CART_FOUND("cart.found");
	
	private final String type;

	private ExceptionConstants(String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return this.type;
	}
}
