package com.infy.user.util;

public enum UserConstants {
	CUSTOMER_NOT_FOUND("customer.not.found"),
	CUSTOMER_INVALID_INPUT("customer.invalid.input"),
	GENERAL_EXCEPTION_MESSAGE("general.exception"),
	USER_ALREADY_EXISTS("user.already.exists"),
	NOT_NULL_USER_FIELDS("not.null.user.fields"),
	ADDRESS_NOT_FOUND("address.not.found"),
	INVALID_INPUT("invalid.input"),
	USER_NOT_RELATED_TO_ADDRESS("user.not.related.to.address"),
	USER_NOT_FOUND("user.not.found");
	
	private final String type;
	private UserConstants(String type) {
	this.type = type;
	}
	@Override
	public String toString() {
	return this.type;
	}


}
